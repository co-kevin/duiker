package com.zdan91.duiker.config.swagger;

import java.util.Collections;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.swagger.models.Swagger;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

@Profile({"swagger"})
@Component
public class SwaggerGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerGeneratorService.class);

    @Autowired
    private SwaggerMvcGenerator mvcGenerator;

    private Swagger swagger;

    public Swagger getSwagger() {

        if (this.swagger != null) {
            return this.swagger;
        }

        Swagger springmvc = mvcGenerator.getSwagger();

        Swagger _swagger = springmvc;

        try {
            _swagger.setBasePath("omniprimeincBasePath");
            Collections.sort(_swagger.getTags(), (o1, o2) -> {
                return o1.getName().compareTo(o2.getName());
            });
        } catch (Exception e) {
            logger.debug("error :", e);
        }

        logger.debug("All mappings :", _swagger.getPaths().keySet().stream().collect(Collectors.joining("|")));


        this.swagger = _swagger;

        return _swagger;
    }

    @Component
    public static class SwaggerMvcGenerator {
        @Autowired(required = false)
        private ServiceModelToSwagger2Mapper mapper;

        @Autowired(required = false)
        private DocumentationCache documentationCache;

        private Swagger swagger;

        public synchronized Swagger getSwagger() {
            if (swagger != null) {
                return swagger;
            }

            Swagger swagger = null;
            if (mapper == null || documentationCache == null) {
                return swagger;
            }

            Documentation documentation = documentationCache.documentationByGroup("1_UserPath");
            if (documentation != null) {
                swagger = mapper.mapDocumentation(documentation);
                swagger.getPaths().values().forEach(item -> {
                    item.getOperations().forEach(obj -> {
                        obj.getVendorExtensions().put("type", "swagger-springmvc");
                    });
                });
            }

            this.swagger = swagger;
            return swagger;
        }
    }
}
