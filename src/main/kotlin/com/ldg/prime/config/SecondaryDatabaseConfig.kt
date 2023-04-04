package com.ldg.prime.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager",
    basePackages = ["com.ldg.prime.maria.slave.repository"]
)
class SecondaryDatabaseConfig {

    @Bean(name = ["secondaryDataSource"])
    @ConfigurationProperties(prefix = "secondary.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean(name = ["secondaryEntityManagerFactory"])
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("secondaryDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val properties = mutableMapOf<String, Any>()
        properties["hibernate.dialect"] = "org.hibernate.dialect.MariaDBDialect"
        properties["jakarta.persistence.read-only"] = true
        return builder
            .dataSource(dataSource)
            .packages("com.ldg.prime.maria.slave")
            .persistenceUnit("secondary")
            .properties(properties)
            .build()
    }

    @Bean(name = ["secondaryTransactionManager"])
    fun transactionManager(
        @Qualifier("secondaryEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}