package com.popov.calculator.domain.repository

import com.popov.calculator.domain.model.MachineStatus
import com.popov.calculator.domain.model.Order
import com.popov.calculator.domain.model.Resources

interface CoffeeRepository {
    fun status(): MachineStatus

    fun tryBuy(order: Order): BuyResult

    fun fill(add: Resources)

    fun takeMoney(): Int
}
