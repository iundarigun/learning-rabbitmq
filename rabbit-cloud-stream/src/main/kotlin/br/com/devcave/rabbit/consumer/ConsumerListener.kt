package br.com.devcave.rabbit.consumer

import br.com.devcave.rabbit.domain.Person
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component

@Component
class ConsumerListener {
    private val log = LoggerFactory.getLogger(javaClass)

    @StreamListener("input")
    fun listen(message: Person) {
        log.info("M=listen, message=$message")
    }
}