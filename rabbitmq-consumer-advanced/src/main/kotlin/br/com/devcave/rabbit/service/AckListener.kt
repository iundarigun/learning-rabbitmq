package br.com.devcave.rabbit.service

import br.com.devcave.rabbit.domain.Person
import com.rabbitmq.client.Channel
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

@Service
class AckListener {
    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["FIRST-QUEUE-ADVANCED"], ackMode = "MANUAL", concurrency="5-6")
    fun consumerFirstQueue(person: Person, channel: Channel, @Header(AmqpHeaders.DELIVERY_TAG) tag: Long?) {
        log.info("person $person")
        if (person.collageCompletedYear == null) {
            log.warn("message wrong")
            channel.basicNack(tag ?: 0L, false, false)
        } else {
            log.info("message Ok")
            channel.basicAck(tag ?: 0L, false)
        }
    }
}