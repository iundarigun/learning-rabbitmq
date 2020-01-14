package br.com.devcave.rabbit.producer

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface Producer2Client {
    @Output("output2")
    fun output(): MessageChannel
}