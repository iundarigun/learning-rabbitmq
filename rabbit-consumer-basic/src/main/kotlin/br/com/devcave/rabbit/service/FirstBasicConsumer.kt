package br.com.devcave.rabbit.service

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class FirstBasicConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["FIRST-QUEUE-BASIC"])
    fun receiveMessageFromFirstQueue(message: Message) {
        log.info("receive message from ${message.messageProperties.consumerQueue}")
        val bodyAsString = message.body?.let { String(it) } ?: ""
        log.info("body $bodyAsString")
    }
}