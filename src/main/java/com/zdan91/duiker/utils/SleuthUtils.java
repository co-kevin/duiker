package com.zdan91.duiker.utils;

import java.lang.reflect.Field;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "spring.sleuth.enabled", matchIfMissing = true)
public class SleuthUtils implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SleuthUtils.class);

    public static Tracer tracer;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Async
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (applicationContext == event.getApplicationContext()) {
            Optional<Tracer> option = applicationContext.getBeansOfType(Tracer.class).values().stream().findFirst();
            if (option.isPresent()) {
                tracer = option.get();
            } else {
                logger.info("no zipkin set, can not provide bagagge.");
            }
        }
    }

    public static void mustReportNow() {
        if (tracer != null) {
            Span span = tracer.getCurrentSpan();
            if (span != null) {
                setSpanMustReport(span);
            }
        }
    }

    public static void tag(String key, String value) {
        if (tracer != null) {
            Span span = tracer.getCurrentSpan();
            if (span != null) {
                span.tag(key, value);
            }
        }
    }

    public static String getBaggage(String key) {
        if (tracer != null) {
            Span span = tracer.getCurrentSpan();
            if (span != null) {
                return span.getBaggageItem(key);
            }
        }
        return null;
    }

    public static void setBaggage(String key, String value) {
        if (tracer != null) {
            Span span = tracer.getCurrentSpan();
            if (span != null) {
                span.setBaggageItem(key, value);
            }
        }
    }

    private static Field span_remote = null;
    private static Field span_exportable = null;

    static {
        try {
            span_remote = Span.class.getDeclaredField("remote");
            span_exportable = Span.class.getDeclaredField("exportable");

            span_remote.setAccessible(true);
            span_exportable.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            logger.warn("span extract error :", e);
        }
    }

    static void setSpanMustReport(Span span) {
        try {
            span_remote.set(span, false);
            span_exportable.set(span, true);
        } catch (Exception e) {
            logger.debug("setSpanRemote error :" + e.getMessage());
        }
    }
}
