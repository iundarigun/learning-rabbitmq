package br.com.devcave.rabbit.consumer

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface ConsumerClient {
    @Input("input")
    fun input(): SubscribableChannel
}