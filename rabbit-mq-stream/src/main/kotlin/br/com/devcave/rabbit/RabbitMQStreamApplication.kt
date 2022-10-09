package br.com.devcave.rabbit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitMqStreamApplication

fun main(args: Array<String>) {
	runApplication<RabbitMqStreamApplication>(*args)
}
