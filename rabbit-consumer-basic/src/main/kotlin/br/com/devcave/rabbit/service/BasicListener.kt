package br.com.devcave.rabbit.service

import brave.Span
import brave.Tracer
import brave.Tracing
import brave.spring.rabbit.SpringRabbitTracing
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.stereotype.Service

@Service
class BasicListener : MessageListener {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun onMessage(message: Message?) {
        log.info("receive message from ${message?.messageProperties?.consumerQueue}")
        val bodyAsString = message?.body?.let { String(it) } ?: ""
        log.info("body $bodyAsString")
    }
}