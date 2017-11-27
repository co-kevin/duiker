package com.zdan91.duiker.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 分布式 Redis 锁, 自动在方法执行前上锁，方法执行后解锁
 * TODO 实现锁的逻辑
 *
 * @author hookszhang
 */
@Documented
@Target(ElementType.METHOD)
public @interface DistributedLock {
    String key() default "duiker-lock-key";
}
