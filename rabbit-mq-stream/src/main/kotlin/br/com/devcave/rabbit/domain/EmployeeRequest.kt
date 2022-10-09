package br.com.devcave.rabbit.domain

import java.time.LocalDate

data class EmployeeRequest(
        val name: String,
        val collageCompletedYear: Int?,
        val bornAt: LocalDate,
        val active: Boolean
)
