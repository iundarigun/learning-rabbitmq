package br.com.devcave.rabbit.domain

import java.time.LocalDate

data class Person(
    val name: String,
    val collageCompletedYear: Int?,
    val bornAt: LocalDate,
    val active: Boolean
)