package com.popov.calculator.domain.interactor

import com.popov.calculator.domain.model.Order
import com.popov.calculator.domain.repository.BuyLacking
import com.popov.calculator.domain.repository.BuySuccess
import com.popov.calculator.domain.repository.CoffeeRepository

class BuyCoffeeInteractor(
    private val repository: CoffeeRepository,
) {
    operator fun invoke(order: Order): InteractorOutcome {
        val message =
            when (val result = repository.tryBuy(order)) {
                is BuySuccess -> "Enjoy your ${result.coffee.lowercase()}!"
                is BuyLacking -> "Sorry, not enough ${result.resource}!"
            }
        return InteractorOutcome(message, repository.status())
    }
}
