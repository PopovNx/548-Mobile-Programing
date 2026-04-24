package com.popov.calculator.mvp

import com.popov.calculator.domain.model.MachineStatus

interface CoffeeView {
    fun renderStatus(status: MachineStatus)

    fun renderMessage(message: String)
}
