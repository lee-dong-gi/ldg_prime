package com.ldg.prime.config

import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.sql.Connection
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    //transactionManagerRef = "primaryTransactionManager",
    basePackages = ["com.ldg.prime.maria.master.repository"]
)
class JpaConfig {
    @Bean(name = ["writeDataSource"])
    @ConfigurationProperties(prefix = "primary.datasource")
    fun writeDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean(name = ["readDataSource"])
    @ConfigurationProperties(prefix = "secondary.datasource")
    fun readDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Primary
    @Bean
    fun routingDataSource(): DataSource {
        val routingDataSource = RoutingDataSource()
        val readDataSourceProxy = LazyConnectionDataSourceProxy(readDataSource())
        val writeDataSourceProxy = LazyConnectionDataSourceProxy(writeDataSource())

        routingDataSource.setTargetDataSources(mapOf("readDataSource" to readDataSourceProxy, "writeDataSource" to writeDataSourceProxy))
        routingDataSource.setDefaultTargetDataSource(readDataSourceProxy)

        return routingDataSource
    }

    @Primary
    @Bean(name = ["primaryEntityManagerFactory"])
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()
        entityManagerFactory.dataSource = routingDataSource()
        entityManagerFactory.setPackagesToScan("com.ldg.prime.maria.master.entity")
        entityManagerFactory.jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactory.setJpaProperties(hibernateProperties())
        entityManagerFactory.persistenceUnitName = "prime"
        return entityManagerFactory
    }

//    @Primary
//    @Bean(name = ["primaryTransactionManager"])
//    fun transactionManager(@Qualifier("primaryEntityManagerFactory") entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
//        return JpaTransactionManager(entityManagerFactory)
//    }

    private fun hibernateProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
        properties.setProperty("hibernate.show_sql", "true")
        properties.setProperty("hibernate.format_sql", "true")
        return properties
    }

    class RoutingDataSource : AbstractRoutingDataSource() {
        private val log = LoggerFactory.getLogger(javaClass)
        public override fun determineCurrentLookupKey(): Any {
            log.error("{} determineCurrentLookupKey isCurrentTransactionReadOnly", TransactionSynchronizationManager.isCurrentTransactionReadOnly().toString())
            return if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) "readDataSource" else "writeDataSource"
        }
    }
}