package com.zdan91.duiker.config

import com.baomidou.mybatisplus.plugins.PaginationInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Mybatis, Mybatis Plus Configuration
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
open class MybatisConfiguration {

    /**
     * 分页插件
     */
    @Bean
    open fun paginationInterceptor(): PaginationInterceptor {
        return PaginationInterceptor()
    }
}
