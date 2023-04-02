package com.ldg.prime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class PrimeApplication

fun main(args: Array<String>) {
	runApplication<PrimeApplication>(*args)
}
