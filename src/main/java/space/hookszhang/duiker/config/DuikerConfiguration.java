package space.hookszhang.duiker.config;

import space.hookszhang.duiker.config.swagger.SwaggerConfiguration;
import space.hookszhang.duiker.utils.LogbackUtils;
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
