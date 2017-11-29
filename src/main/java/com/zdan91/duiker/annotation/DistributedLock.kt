package com.zdan91.duiker.annotation

import java.lang.annotation.Documented

/**
 * 分布式 Redis 锁, 自动在方法执行前上锁，方法执行后解锁
 * TODO 实现锁的逻辑
 *
 * @author hookszhang
 */
@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class DistributedLock(val key: String = "duiker-lock-key")
