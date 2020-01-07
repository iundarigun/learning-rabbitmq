package br.com.devcave.rabbit.config

import br.com.devcave.rabbit.service.BasicListener
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.MessageListenerContainer
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ConsumerConfig(
    private val connectionFactory: ConnectionFactory,
    private val basicListener: BasicListener
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun queueBasic(): Queue {
        return QueueBuilder
            .durable("FIRST-QUEUE-BASIC")
            .build()
    }

    @Bean
    fun listenerContainer(): MessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueues(queueBasic())
        container.setMessageListener(basicListener)
        return container
    }

    @RabbitListener(queues = ["SECOND-QUEUE-BASIC"])
    fun receiveMessageFromTopic1(message: String) {
        log.info("message from SECOND-QUEUE $message")
    }
}