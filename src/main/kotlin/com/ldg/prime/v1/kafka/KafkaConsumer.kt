package com.ldg.prime.v1.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.io.IOException


@Service
class KafkaConsumer {
    private val log = LoggerFactory.getLogger(javaClass)
    @KafkaListener(topics = ["exam-topic"], groupId = "foo")
    @Throws(IOException::class)
    fun consume(message: String) {
        log.info("Consumed message : {}", message)
    }
}