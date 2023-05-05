package com.ldg.prime.v1.kafka

import com.ldg.prime.v1.kafka.domain.KafkaStreamProcessor
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import org.springframework.web.socket.*
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import reactor.core.publisher.Flux
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@RestController
@EnableWebSocket
class KafkaCaptureScreenController (
    val kafkaProducerService: KafkaProducerService
) : WebSocketConfigurer {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(ScreenCaptureHandler(kafkaProducerService), "/screen-capture")
    }

    @EnableWebSocket
    class ScreenCaptureHandler(private val kafkaProducerService: KafkaProducerService) : BinaryWebSocketHandler() {
        private val robot = Robot()
        private var session: WebSocketSession? = null

        override fun afterConnectionEstablished(session: WebSocketSession) {
            this.session = session
        }

        override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
            this.session = null
        }
        init {
            // 화면 캡처 스레드 시작
            Thread {
                while (true) {
                    try {
                        // 화면 캡처 수행
                        val capture = robot.createScreenCapture(Rectangle(Toolkit.getDefaultToolkit().screenSize))
                        // BufferedImage를 JPEG 이미지 바이트 배열로 변환
                        val outputStream = ByteArrayOutputStream()
                        ImageIO.write(capture, "jpeg", outputStream)
                        val imageBytes = outputStream.toByteArray()
                        // 클라이언트에 이미지 전송
                        this.session?.sendMessage(BinaryMessage(imageBytes))
                        //kafkaProducerService.sendCaptureScreen(imageBytes)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // 화면 캡처 간격 (100ms)
                    Thread.sleep(100)
                }
            }.start()
        }

        override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
            // 클라이언트로부터의 메시지 수신 처리
        }
    }
}