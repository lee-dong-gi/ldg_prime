package com.ldg.prime.config

import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "masterEntityManagerFactory",
    basePackages = ["com.ldg.prime.v1.maria.*"] //지정한 경로의 repository 모두 단방향 이중화 설정을 적용
)
class JpaConfig {
    @Bean(name = ["masterDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.master")
    fun masterDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean(name = ["slaveDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    fun slaveDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Primary
    @Bean
    fun routingDataSource(): DataSource {
        val routingDataSource = RoutingDataSource()
        val readDataSourceProxy = LazyConnectionDataSourceProxy(slaveDataSource())
        val writeDataSourceProxy = LazyConnectionDataSourceProxy(masterDataSource())

        routingDataSource.setTargetDataSources(mapOf("slaveDataSource" to readDataSourceProxy, "masterDataSource" to writeDataSourceProxy))
        routingDataSource.setDefaultTargetDataSource(readDataSourceProxy) // 기본 data source는 slave로 설정

        return routingDataSource
    }

    @Primary
    @Bean(name = ["masterEntityManagerFactory"])
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()
        entityManagerFactory.dataSource = routingDataSource()
        entityManagerFactory.setPackagesToScan("com.ldg.prime.v1.maria.entity")
        entityManagerFactory.jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactory.persistenceUnitName = "prime"
        return entityManagerFactory
    }

    class RoutingDataSource : AbstractRoutingDataSource() {
        private val log = LoggerFactory.getLogger(javaClass)
        public override fun determineCurrentLookupKey(): Any {
            log.error("{} determineCurrentLookupKey isCurrentTransactionReadOnly", TransactionSynchronizationManager.isCurrentTransactionReadOnly().toString())
            return if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) "readDataSource" else "writeDataSource"
        }
    }

    @Bean(name = ["masterTransactionManager"])
    fun masterTransactionManager(@Qualifier("masterEntityManagerFactory") emFactory: EntityManagerFactory?) : JpaTransactionManager{
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = emFactory
        return transactionManager
    }

    @Primary
    @Bean(name = ["slaveTransactionManager"])
    fun slaveTransactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(routingDataSource())
    }
}