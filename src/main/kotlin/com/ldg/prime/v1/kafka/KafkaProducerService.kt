package com.ldg.prime.v1.kafka

import lombok.RequiredArgsConstructor
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.io.*
import java.util.*
import javax.imageio.ImageIO


@Service
@RequiredArgsConstructor
class KafkaProducerService(
    val kafkaTemplate: KafkaTemplate<String, ByteArray>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null

    fun sendMessage(message: String) {
        log.info("Produce message : {}", message)
        kafkaTemplate.send(TOPIC, message.toByteArray())
    }

    fun sendCaptureScreen(byteArray: ByteArray) {
//        kafkaTemplate.send(TOPIC,captureScreen())
        kafkaTemplate.send(TOPIC, byteArray)
    }

    private fun captureScreen(): ByteArray {
        val robot = Robot()
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val captureSize = Rectangle(0, 0, screenSize.width, screenSize.height)
        val capture = robot.createScreenCapture(captureSize)
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(capture, "png", outputStream)
        return outputStream.toByteArray()
    }

    companion object {
        private const val TOPIC = "exam-topic"
    }
}