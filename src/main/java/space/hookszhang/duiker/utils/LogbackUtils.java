package space.hookszhang.duiker.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackUtils {

    public static void prepareTraceIdFilter() {
        List<Logger> loggerList = ((LoggerContext) LoggerFactory.getILoggerFactory()).getLoggerList();
        for (Logger logger : loggerList) {
            Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders();
            while (it.hasNext()) {
                Appender<ILoggingEvent> appender = it.next();
                if (AsyncAppender.class.isInstance(appender)) {
                    Iterator<Appender<ILoggingEvent>> subAppenderIt = AsyncAppender.class.cast(appender).iteratorForAppenders();
                    if (subAppenderIt.hasNext()) {
                        appender = subAppenderIt.next();
                    }
                }

                appender.addFilter(new LogbackTraceFilter());
            }
        }
    }

    static class LogbackTraceFilter extends Filter<ILoggingEvent> {

        @Override
        public FilterReply decide(ILoggingEvent event) {
            if (SleuthUtils.tracer != null
                && SleuthUtils.tracer.isTracing()) {

                try {
                    String traceId = String.valueOf(SleuthUtils.tracer.getCurrentSpan().getTraceId());
                    String newMsg = "[TraceId:" + traceId + "]  " + event.getMessage();
                    String newFormattedMsg = "[TraceId:" + traceId + "]  " + event.getFormattedMessage();
                    logginEvent_Message.set(event, newMsg);
                    logginEvent_FormattedMessage.set(event, newFormattedMsg);
                } catch (Exception e) {

                }
            }
            return FilterReply.ACCEPT;
        }

        private static final Field logginEvent_Message = getLogginEventField("message");

        private static final Field logginEvent_FormattedMessage = getLogginEventField("formattedMessage");

        private static Field getLogginEventField(String name) {
            try {
                Field field = LoggingEvent.class.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
