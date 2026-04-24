package com.popov.calculator.mvp

import com.popov.calculator.domain.interactor.BuyCoffeeInteractor
import com.popov.calculator.domain.interactor.FillResourcesInteractor
import com.popov.calculator.domain.interactor.TakeMoneyInteractor
import com.popov.calculator.domain.model.Coffee
import com.popov.calculator.domain.model.Order
import com.popov.calculator.domain.model.Resources
import com.popov.calculator.domain.repository.CoffeeRepository

class CoffeePresenterImpl(
    private val repository: CoffeeRepository,
    private val buyCoffee: BuyCoffeeInteractor = BuyCoffeeInteractor(repository),
    private val fillResources: FillResourcesInteractor = FillResourcesInteractor(repository),
    private val takeMoney: TakeMoneyInteractor = TakeMoneyInteractor(repository),
) : CoffeePresenter {
    private var view: CoffeeView? = null

    override fun attach(view: CoffeeView) {
        this.view = view
        view.renderStatus(repository.status())
        view.renderMessage("Ready")
    }

    override fun detach() {
        view = null
    }

    override fun onBuy(coffee: Coffee) {
        val outcome = buyCoffee(Order(coffee))
        view?.renderStatus(outcome.status)
        view?.renderMessage(outcome.message)
    }

    override fun onFill(add: Resources) {
        val outcome = fillResources(add)
        view?.renderStatus(outcome.status)
        view?.renderMessage(outcome.message)
    }

    override fun onTakeMoney() {
        val outcome = takeMoney()
        view?.renderStatus(outcome.status)
        view?.renderMessage(outcome.message)
    }
}
