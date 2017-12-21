package com.zdan91.duiker.config;

import com.zdan91.duiker.predicate.BlueGreenPredicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.ZoneAvoidancePredicate;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

@Configuration
public class BlueGreenConfiguration {

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshed(ContextRefreshedEvent event) throws IllegalArgumentException, IllegalAccessException {
        if (event.getApplicationContext() instanceof AnnotationConfigApplicationContext) {
            AnnotationConfigApplicationContext tagetContext = (AnnotationConfigApplicationContext) event.getApplicationContext();
            String serverName = tagetContext.getEnvironment().getProperty("ribbon.client.name");
            if (StringUtils.isNotBlank(serverName)) {
                ZoneAvoidanceRule rule = tagetContext.getBean("ribbonRule", ZoneAvoidanceRule.class);
                IClientConfig config = tagetContext.getBean("ribbonClientConfig", IClientConfig.class);

                ZoneAvoidancePredicate zonePredicate = new ZoneAvoidancePredicate(rule, config);
                AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(rule, config);
                BlueGreenPredicate blueGreenPredicate = new BlueGreenPredicate(rule, config);

                CompositePredicate compositePredicate = CompositePredicate.withPredicates(blueGreenPredicate, zonePredicate, availabilityPredicate).addFallbackPredicate(availabilityPredicate).addFallbackPredicate(AbstractServerPredicate.alwaysTrue()).build();

                FieldUtils.getDeclaredField(ZoneAvoidanceRule.class, "compositePredicate", true).set(rule, compositePredicate);
            }
        }
    }
}

