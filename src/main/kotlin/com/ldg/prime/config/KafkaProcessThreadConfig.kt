package com.ldg.prime.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@EnableAsync
@Configuration
class KafkaProcessThreadConfig {
    /**
     * ThreadPoolTaskExecutor - 비동기로 실행될 스레드를 실행합니다
     * setCorePoolSize() - 스레드 풀에 생성될 수 있는 스레드의 개수입니다.
     * setMaxPoolsize() - 기존의 pool 사이즈가 동적으로 늘어날 수 있는 최대치입니다
     * setQueueCapacity()
     * - core pool size 보다 적은 스레드가 실행된다면, queue가 아닌 새로운 스레드를 만듭니다
     * - core pool size와 같거나 큰 스레드가 필요하다면, queue에 올립니다.
     * - queue가 가득 차고, max pool size에 여유 있다면 새로운 스레드를 생성합니다
     *
     */
    @Bean(name = ["kafkaProcessThreadPoolTaskExecutor"])
    fun threadPoolTaskExecutor(): Executor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.corePoolSize = 10
        taskExecutor.maxPoolSize = 50
        taskExecutor.queueCapacity = 50
        taskExecutor.setThreadNamePrefix("kafkaProcessThread-")
        taskExecutor.initialize()
        return taskExecutor
    }
}
