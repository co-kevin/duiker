package com.zdan91.duiker.config.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import io.swagger.models.Swagger;

@Profile({"swagger"})
@Component
@ManagedResource(description = "swagger管理")
public class SwaggerReporter implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SwaggerReporter.class);

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private SwaggerGeneratorService swaggerGenerator;

    @Autowired
    private SwaggerRegistryService swaggerRegistryService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext() != applicationContext)
            return;
        handleSwagger();
    }

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void handleSwagger() {
        Swagger swagger = null;

        try {
            swagger = swaggerGenerator.getSwagger();
        } catch (Exception e) {
            log.info("generator swagger error :", e);
        }

        if (swagger != null) {
            ListenableFuture<Integer> future = swaggerRegistryService.registry(swagger);

            future.addCallback(
                count -> {
                    log.info(count + " swaggerserver has bean registered");
                }, ex -> {
                    log.info("swagger exception occured :", ex);
                });
        }

        log.info("swagger registered");
    }

    @ManagedOperation(description = "重新上传swagger")
    public boolean reloadSwagger() {
        handleSwagger();
        return true;
    }
}
