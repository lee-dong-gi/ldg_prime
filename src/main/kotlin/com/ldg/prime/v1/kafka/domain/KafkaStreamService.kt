package com.ldg.prime.v1.kafka.domain

import com.ldg.prime.v1.common.aop.ScheduledSwitch
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*


@Component
@EnableScheduling
@ScheduledSwitch
class KafkaStreamService(
    val kafkaStreamProcessor: KafkaStreamProcessor,
    val kafkaProcess: KafkaProcess
) {
    @Scheduled(fixedDelay = 100) // 80 이하로 내리면 async thread deadLock 현상 발생
    @Async("kafkaProcessThreadPoolTaskExecutor")
    fun processSchedule() {
        //최대 Thread 개수 체크
        val threadSet = Thread.getAllStackTraces()
            .keys
            .stream()
            .filter { thread: Thread ->
                thread.name.startsWith("kafkaProcessThread-")
            }

        if (threadSet.count() > 50) return

        if (kafkaStreamProcessor.bufferQueue.isEmpty()) return

        doProcess(kafkaStreamProcessor.bufferQueue)
    }

    fun doProcess(bufferQueue: Queue<String?>) {
        val target = bufferQueue.poll() // 다른 곳에서 선점하지 못하도록 미리 빼둠
        try {
            val dataPackage = target?.let { KafkaProcessParamPackage(it) } ?: KafkaProcessParamPackage("")
            KafkaProcess.Operator.values().forEach { type -> kafkaProcess.doProcess(dataPackage, type) }
        } catch (e: Exception) {
            bufferQueue.add(target) // 빼둔거 다시 집어넣음
        }
    }

}