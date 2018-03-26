package space.hookszhang.duiker.config.swagger;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.models.Swagger;

@Component
@Profile({"swagger"})
public class SwaggerRegistryService implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SwaggerRegistryService.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private RestTemplate restSwaggerTemplate = new RestTemplate();

    public SwaggerRegistryService() {
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        restSwaggerTemplate.getMessageConverters().remove(1);
        restSwaggerTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    @Autowired
    private DiscoveryClient discovery;

    @Async
    public ListenableFuture<Integer> registry(Swagger swagger) {
        if (swagger == null)
            return new AsyncResult<Integer>(0);

        selfRegistry(swagger);

        log.debug("swagger registry start.");

        List<ServiceInstance> instances = discovery.getServices()
            .parallelStream()
            .map(discovery::getInstances)
            .flatMap(List::stream)
            .filter(item -> "true".equals(item.getMetadata().get("isSwaggerServer")))
            .collect(Collectors.toList());

        int successCount = 0;

        Exception pendingException = null;

        if (instances.isEmpty()) {
            log.debug("no [swagger] application in cloud.");
        }

        for (ServiceInstance instance : instances) {
            try {
                String url = SwaggerUtils.findUrl(discovery, instance.getServiceId(), "registySwagger");
                if (StringUtils.isNotBlank(url)) {
                    HttpHeaders header = new HttpHeaders();
                    header.set("X-B3-PORT", String.valueOf(instance.getPort()));
                    header.set("X-B3-HOST", instance.getHost());
                    header.setContentType(MediaType.TEXT_PLAIN);
                    HttpEntity<String> entity = new HttpEntity<String>(objectMapper.writeValueAsString(swagger), header);
                    HttpStatus stat = restSwaggerTemplate.postForObject(url, entity, HttpStatus.class);
                    if (stat == HttpStatus.OK) {
                        successCount++;
                    } else {
                        log.debug("fail to upload swagger to server.", stat);
                    }
                }
            } catch (Exception e) {
                pendingException = e;
                log.debug("error to upload swagger data.", e);
            }
        }

        if (pendingException != null) {
            throw new SwaggerException("success count:" + successCount, pendingException);
        }

        return new AsyncResult<Integer>(successCount);
    }

    private boolean selfRegistry(Swagger swagger) {
        if (!"true".equals(System.getProperty("eureka.instance.metadataMap.isSwaggerServer")))
            return false;

        try {
            Object registryController = applicationContext.getBean(Class.forName("com.omniprimeinc.component.swagger.controller.RegistryController"));
            for (Method method : registryController.getClass().getDeclaredMethods()) {
                if ("registySwagger".equals(method.getName())) {
                    method.invoke(registryController, objectMapper.writeValueAsString(swagger));
                    log.debug("swagger selfRegistry completed.");
                    return true;
                }
            }
        } catch (Exception e) {
            log.debug("error selfRegistry :", e);
        }

        return false;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static class SwaggerException extends RuntimeException {
        private static final long serialVersionUID = -5107181804202141997L;

        public SwaggerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
