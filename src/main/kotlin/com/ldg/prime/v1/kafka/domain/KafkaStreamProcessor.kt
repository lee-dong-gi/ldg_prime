package com.ldg.prime.v1.kafka.domain

import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import lombok.extern.slf4j.Slf4j
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.processor.PunctuationType
import org.apache.kafka.streams.processor.api.Processor
import org.apache.kafka.streams.processor.api.ProcessorContext
import org.apache.kafka.streams.processor.api.ProcessorSupplier
import org.apache.kafka.streams.processor.api.Record
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


@Slf4j
@Component
@RequiredArgsConstructor
class KafkaStreamProcessor(val streamsInfo: KafkaStreamsInfo): InitializingBean {
    private val log = LoggerFactory.getLogger(javaClass)

    @Getter
    @Setter
    val bufferQueue: Queue<String?> = ConcurrentLinkedQueue()
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null

    @Scheduled(fixedDelay = 1000)
    fun healthSchedule() {
        if (existsProblem(streamsInfo.getStreams())) {
            streamReset()
        }
    }

    override fun afterPropertiesSet() {
        if (streamsInfo.getStreams() != null)
            return

        streamReset()
    }

    private fun getKStreamsConfig(): Properties {
        val props = Properties()
        props[StreamsConfig.APPLICATION_ID_CONFIG] = "foo"
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        props[StreamsConfig.consumerPrefix(ConsumerConfig.MAX_POLL_RECORDS_CONFIG)] = 50
        props[StreamsConfig.NUM_STREAM_THREADS_CONFIG] = 1
        props[StreamsConfig.COMMIT_INTERVAL_MS_CONFIG] = 0
        return props
    }

    private fun streamReset() {
        val properties: Properties = getKStreamsConfig()
        val builder = StreamsBuilder()
        val stream = builder.stream<String, String>("exam-topic")
        stream.process(
            ProcessorSupplier<String, String, Void, Void> {
                object : Processor<String?, String?, Void?, Void?> {
                    override fun init(context: ProcessorContext<Void?, Void?>) {
                        try{
                            super.init(context)
                            context.schedule(Duration.ofSeconds(1000), PunctuationType.WALL_CLOCK_TIME) { _: Long -> }
                        } catch (e: Exception) {
                            close()
                        }
                    }

                    override fun process(record: Record<String?, String?>) {
                        try {
                            loadingBuffer(bufferQueue, record)
                        } catch (e: Exception) {
                            close()
                        }
                    }

                    override fun close() {
                        super.close()
                        streamsInfo.closeStreams()
                    }

                    fun pause() {
                        Thread.sleep(200)
                    }

                    fun loadingBuffer(queue: Queue<String?>, record: Record<String?, String?>) {
                        queue.add(record.value())

                        // 무조건 add 되고나서 pause(데이터 누락 방지)
                        if (bufferQueue.size == 50
                            || bufferQueue.size > 50
                        ) {
                            var pauseFlag = true
                            while (pauseFlag) {
                                pause()
                                if (bufferQueue.size < 50) {
                                    pauseFlag = false
                                }
                            }
                        }
                    }
                }
            }
        )
        val localStreams = KafkaStreams(builder.build(), properties)
        if (existsProblem(localStreams)) streamReset()
        localStreams.setStateListener { newState: KafkaStreams.State?, oldState: KafkaStreams.State? ->
            log.error("newState = {} / oldState = {}", newState.toString(), oldState.toString());
        }
        streamsInfo.setKafkaStreams(localStreams)
        streamsInfo.getStreams()!!.start()
    }
    
    private fun existsProblem(streams: KafkaStreams?): Boolean {
        if (streams == null
            || bufferQueue.size == 50
            || bufferQueue.size > 50 // -> 즉시 적용(Buffer Queue Size)
        ) {
            return false
        }
        return (streams.state() == KafkaStreams.State.NOT_RUNNING
                || streams.state() == KafkaStreams.State.ERROR
                || streams.state() == KafkaStreams.State.PENDING_SHUTDOWN)
    }
}