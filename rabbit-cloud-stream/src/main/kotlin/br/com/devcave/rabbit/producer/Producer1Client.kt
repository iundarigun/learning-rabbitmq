package br.com.devcave.rabbit.producer

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface Producer1Client {
    @Output("output1")
    fun output(): MessageChannel
}