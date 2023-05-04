package com.ldg.prime.v1.kafka

import com.ldg.prime.v1.kafka.domain.KafkaStreamProcessor
import lombok.RequiredArgsConstructor
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


@Service
@RequiredArgsConstructor
class KafkaProducerService (
    val kafkaTemplate: KafkaTemplate<String, String>
){
    private val log = LoggerFactory.getLogger(javaClass)
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null

    fun sendMessage(message: String) {
        log.info("Produce message : {}", message)
        kafkaTemplate.send(TOPIC, message)
    }

    fun sendFile(filePath: String){
        val props = Properties()
        props["bootstrap.servers"] = bootstrapAddress
        props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        props["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"

        val producer: Producer<String, String> = KafkaProducer(props)

        try {
            FileInputStream(filePath).use { inputStream ->
                InputStreamReader(inputStream).use { inputStreamReader ->
                    BufferedReader(inputStreamReader).use { reader ->
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            producer.send(ProducerRecord(TOPIC, line))
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            producer.close()
        }
    }

    companion object {
        private const val TOPIC = "exam-topic"
    }
}