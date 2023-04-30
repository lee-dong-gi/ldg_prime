package com.ldg.prime.v1.kafka.domain

import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@RequiredArgsConstructor
class KafkaProcess : InitializingBean {
    private val log = LoggerFactory.getLogger(javaClass)

    enum class Operator { DATA_PREPARE, CALC, LOGGING }

    val operators = EnumMap<Operator, (KafkaProcessParamPackage) -> KafkaProcessParamPackage>(Operator::class.java)

    override fun afterPropertiesSet() {
        operators.apply {
            put(Operator.DATA_PREPARE) { param -> dataPrepare(param)}
            put(Operator.CALC) { param -> calc(param) }
            put(Operator.LOGGING) { param -> logging(param) }
        }
    }

    fun dataPrepare(kafkaProcessParamPackage: KafkaProcessParamPackage): KafkaProcessParamPackage {
        log.error("dataPrepare :: " + kafkaProcessParamPackage.kafkaRawData)
        return kafkaProcessParamPackage
    }

    fun calc(kafkaProcessParamPackage: KafkaProcessParamPackage): KafkaProcessParamPackage {
        log.error("calc :: "  + kafkaProcessParamPackage.kafkaRawData)
        return kafkaProcessParamPackage
    }

    fun logging(kafkaProcessParamPackage: KafkaProcessParamPackage): KafkaProcessParamPackage {
        log.error("logging :: "  + kafkaProcessParamPackage.kafkaRawData)
        return kafkaProcessParamPackage
    }

    fun doProcess(param: KafkaProcessParamPackage, op: Operator): KafkaProcessParamPackage {
        val opFunc = operators[op] ?: throw IllegalArgumentException("Unknown operator")
        return opFunc(param)
    }
}