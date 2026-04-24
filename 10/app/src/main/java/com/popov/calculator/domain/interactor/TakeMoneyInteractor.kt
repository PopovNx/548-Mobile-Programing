package com.popov.calculator.domain.interactor

import com.popov.calculator.domain.repository.CoffeeRepository

class TakeMoneyInteractor(
    private val repository: CoffeeRepository,
) {
    operator fun invoke(): InteractorOutcome {
        val taken = repository.takeMoney()
        return InteractorOutcome("I gave you $taken UAH", repository.status())
    }
}
