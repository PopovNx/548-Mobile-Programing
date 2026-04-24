package com.popov.calculator.domain.interactor

import com.popov.calculator.domain.model.Resources
import com.popov.calculator.domain.repository.CoffeeRepository

class FillResourcesInteractor(
    private val repository: CoffeeRepository,
) {
    operator fun invoke(add: Resources): InteractorOutcome {
        repository.fill(add)
        return InteractorOutcome("Resources filled", repository.status())
    }
}
