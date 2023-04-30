package com.ldg.prime.v1.common.aop

import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import java.util.concurrent.locks.ReentrantLock


class ScheduledAnnotationBeanPostProcessorCustom : ScheduledAnnotationBeanPostProcessor() {
    @Value(value = "\${prevent.scheduled.tasks:false}")
    private val preventScheduledTasks = false
    private val beans: MutableMap<Any, String> = HashMap()
    private val lock = ReentrantLock(true)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        val switches = AopProxyUtils.ultimateTargetClass(bean).getAnnotation(ScheduledSwitch::class.java)
        if (null != switches) {
            beans[bean] = beanName
            if (preventScheduledTasks) {
                return bean
            }
        }
        return super.postProcessAfterInitialization(bean, beanName)
    }

    fun stop() {
        lock.lock()
        try {
            for ((key, value) in beans) {
                postProcessBeforeDestruction(key, value)
            }
        } finally {
            lock.unlock()
        }
    }

    fun start() {
        lock.lock()
        try {
            for ((key, value) in beans) {
                if (!requiresDestruction(key)) {
                    super.postProcessAfterInitialization(
                        key, value
                    )
                }
            }
        } finally {
            lock.unlock()
        }
    }
}
