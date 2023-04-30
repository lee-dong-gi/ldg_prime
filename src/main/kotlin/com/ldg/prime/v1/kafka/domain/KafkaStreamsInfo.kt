package com.ldg.prime.v1.kafka.domain

import org.apache.kafka.streams.KafkaStreams
import org.springframework.stereotype.Component


@Component
class KafkaStreamsInfo {
    private var streams: KafkaStreams? = null
    fun setKafkaStreams(streams: KafkaStreams?) {
        this.streams = streams
    }

    fun getStreams(): KafkaStreams? {
        return streams
    }

    fun closeStreams() {
        streams?.close()
    }
}
