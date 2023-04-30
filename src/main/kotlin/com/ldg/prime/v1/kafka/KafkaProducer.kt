package com.ldg.prime.v1.kafka

import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class KafkaProducer (val kafkaTemplate: KafkaTemplate<String, String>){
    private val log = LoggerFactory.getLogger(javaClass)

    fun sendMessage(message: String) {
        log.info("Produce message : {}", message)
        kafkaTemplate.send(TOPIC, message)
    }

    companion object {
        private const val TOPIC = "exam-topic"
    }
}