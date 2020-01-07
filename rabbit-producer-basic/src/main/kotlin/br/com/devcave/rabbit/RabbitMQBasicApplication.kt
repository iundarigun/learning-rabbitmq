package br.com.devcave.rabbit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitMQBasicApplication

fun main(args: Array<String>) {
    runApplication<RabbitMQBasicApplication>(*args)
}
