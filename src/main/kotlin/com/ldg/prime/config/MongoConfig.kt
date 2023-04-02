package com.ldg.prime.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Ops
import com.querydsl.core.types.Path
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.Expressions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import kotlin.reflect.KProperty

/**
 * https://jsonobject.tistory.com/559 참고
 */
@Configuration
@EnableMongoRepositories(basePackages = ["com.ldg.prime"])
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