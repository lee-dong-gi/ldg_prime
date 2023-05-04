package com.ldg.prime.v1.kafka

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/kafka")
class KafkaController(val producer: KafkaProducerService) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendMessage(@RequestParam message: String?): String? {
        log.info("message : {}", message)
        producer.sendMessage(message!!)
        return "success"
    }

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sendFile(@RequestParam filePath: String?): String? {
        log.info("filePath : {}", filePath)
        producer.sendFile(filePath!!)
        return "success"
    }
}