package com.zdan91.duiker.config.swagger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile({"swagger"})
@EnableEurekaClient
@ComponentScan
public class SwaggerConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    @Configuration
    @EnableSwagger2
    public static class BeanConfigClass {

        @Value("${spring.application.name}")
        private String appName;

        @Bean
        public Docket userPath() throws ClassNotFoundException {
            String basePackage = SwaggerUtils.basePackage;

            if (StringUtils.isBlank(basePackage)) {
                StackTraceElement[] stacks = new Throwable().getStackTrace();
                if (stacks[stacks.length - 1].getClassName().equals("org.springframework.boot.loader.JarLauncher")) {
                    basePackage = Class.forName(stacks[stacks.length - 9].getClassName()).getPackage().getName();
                } else {
                    basePackage = Class.forName(stacks[stacks.length - 1].getClassName()).getPackage().getName();
                }
            }

            return new Docket(DocumentationType.SWAGGER_2)
                .groupName("1_UserPath")
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new MyApiInfo(appName));
        }
    }

    public static class MyApiInfo extends ApiInfo {
        @SuppressWarnings({"AliDeprecation", "deprecation"})
        public MyApiInfo(String title) {
            super(title, "", "1", "", "", "", "");
        }

        @Override
        public String getDescription() {
            return "omniprimeincSwagger";
        }
    }
}
