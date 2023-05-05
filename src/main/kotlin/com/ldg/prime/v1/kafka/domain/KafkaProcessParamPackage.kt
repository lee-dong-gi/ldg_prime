package com.ldg.prime.v1.kafka.domain

data class KafkaProcessParamPackage (val kafkaRawData: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KafkaProcessParamPackage

        return kafkaRawData.contentEquals(other.kafkaRawData)
    }

    override fun hashCode(): Int {
        return kafkaRawData.contentHashCode()
    }
}