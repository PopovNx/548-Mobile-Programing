package com.popov.calculator.domain.interactor

import com.popov.calculator.domain.model.MachineStatus

data class InteractorOutcome(
    val message: String,
    val status: MachineStatus,
)
