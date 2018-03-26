package space.hookszhang.duiker.config

import liquibase.integration.spring.SpringLiquibase
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.sql.DataSource

/**
 * Liquibase Configuration
 */
@Configuration
@EnableConfigurationProperties(LiquibaseProperties::class)
open class LiquibaseConfiguration {

    private val log = LoggerFactory.getLogger(LiquibaseConfiguration::class.java)

    @Bean
    open fun liquibase(dataSource: DataSource, liquibaseProperties: LiquibaseProperties): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.dataSource = dataSource
        liquibase.changeLog = "classpath:liquibase/master.xml"
        liquibase.contexts = liquibaseProperties.contexts
        liquibase.defaultSchema = liquibaseProperties.defaultSchema
        liquibase.isDropFirst = liquibaseProperties.isDropFirst
        liquibase.setShouldRun(liquibaseProperties.isEnabled)
        log.info("Configuring Liquibase")
        return liquibase
    }
}
