package br.com.devcave.rabbit.config

object QueueDefinition {
    const val FIRST_QUEUE = "FIRST-QUEUE-ADVANCED"
    const val SECOND_QUEUE = "SECOND-QUEUE-ADVANCED"
    const val DIRECT_EXCHANGE = "DIRECT-EXCHANGE-ADVANCED"
    const val FIRST_BINDING_KEY = "TO-FIRST-QUEUE"
    const val SECOND_BINDING_KEY = "TO-SECOND-QUEUE"
    const val DLQ_EXCHANGE = "DLQ-EXCHANGE"
    const val DLQ_QUEUE = "DLQ-QUEUE"
    const val DLQ_BINDING_KEY = "TO-DLQ"
}