package com.popov.calculator.mvp

import com.popov.calculator.domain.model.Coffee
import com.popov.calculator.domain.model.Resources

interface CoffeePresenter {
    fun attach(view: CoffeeView)

    fun detach()

    fun onBuy(coffee: Coffee)

    fun onFill(add: Resources)

    fun onTakeMoney()
}
