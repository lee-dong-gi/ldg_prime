package com.ldg.prime.v1.kafka

import com.ldg.prime.v1.kafka.domain.KafkaStreamProcessor
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux


@RestController
@RequestMapping("/api/v1/kafka")
class KafkaController(
    val producer: KafkaProducerService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendMessage(@RequestParam message: String?): String? {
        log.info("message : {}", message)
        producer.sendMessage(message!!)
        return "success"
    }
}