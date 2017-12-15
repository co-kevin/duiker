package com.zdan91.duiker.config;

import com.zdan91.duiker.config.swagger.SwaggerConfiguration;
import com.zdan91.duiker.utils.LogbackUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(SwaggerConfiguration.class)
public class DuikerConfiguration {
    static {
        LogbackUtils.prepareTraceIdFilter();
    }
}
