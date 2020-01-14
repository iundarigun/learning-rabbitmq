package br.com.devcave.rabbit.controller

import br.com.devcave.rabbit.domain.Person
import br.com.devcave.rabbit.producer.Producer2Client
import br.com.devcave.rabbit.producer.Producer1Client
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("exchanges")
class ProducerController(
    private val producer1Client: Producer1Client,
    private val producer2Client: Producer2Client
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("output1")
    fun postPersonOnExchange(
        @RequestBody message: Person
    ): HttpEntity<Any?> {
        log.info("sending message $message")
        val message: Message<Person> = MessageBuilder.withPayload(message).build()

        producer1Client.output().send(message)

        return ResponseEntity.ok().build()
    }

    @PostMapping("output2")
    fun post2PersonOnExchange(
        @RequestBody message: Person
    ): HttpEntity<Any?> {
        log.info("sending message $message")
        val message: Message<Person> = MessageBuilder.withPayload(message).build()

        producer2Client.output().send(message)

        return ResponseEntity.ok().build()
    }
}
