package com.ldg.prime.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * https://jsonobject.tistory.com/559 참고
 */
@Configuration
@EnableMongoRepositories(basePackages = ["com.ldg.prime.mongo.repository"])
class MongoConfig : AbstractMongoClientConfiguration() {

    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
        return MongoTransactionManager(dbFactory)
    }

    override fun getDatabaseName(): String {
        return "local"
    }

    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString("mongodb://root:root@localhost:27017/admin")
        val mongoClientSettings = MongoClientSettings
                .builder()
                .applyConnectionString(connectionString)
                .build()

        return MongoClients.create(mongoClientSettings)
    }
}