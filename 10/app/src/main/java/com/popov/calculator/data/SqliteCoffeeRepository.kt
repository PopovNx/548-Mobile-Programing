package com.popov.calculator.data

import android.content.Context
import com.popov.calculator.domain.model.MachineStatus
import com.popov.calculator.domain.model.Order
import com.popov.calculator.domain.model.Resources
import com.popov.calculator.domain.repository.BuyLacking
import com.popov.calculator.domain.repository.BuyResult
import com.popov.calculator.domain.repository.BuySuccess
import com.popov.calculator.domain.repository.CoffeeRepository

class SqliteCoffeeRepository(
    context: Context,
) : CoffeeRepository {
    private val helper = CoffeeDbHelper(context.applicationContext)

    override fun status(): MachineStatus =
        helper.readableDatabase
            .rawQuery(
                "SELECT water, milk, beans, cups, money FROM machine WHERE id = 0",
                null,
            ).use { c ->
                c.moveToFirst()
                MachineStatus(
                    resources = Resources(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3)),
                    money = c.getInt(4),
                )
            }

    override fun tryBuy(order: Order): BuyResult {
        val current = status()
        val c = order.coffee
        val lacking =
            listOf(
                "water" to c.water - current.resources.water,
                "milk" to c.milk - current.resources.milk,
                "beans" to c.beans - current.resources.beans,
                "cups" to 1 - current.resources.cups,
            ).firstOrNull { it.second > 0 }?.first
        if (lacking != null) return BuyLacking(lacking)

        helper.writableDatabase.execSQL(
            "UPDATE machine SET water = water - ?, milk = milk - ?, beans = beans - ?, cups = cups - 1, money = money + ? WHERE id = 0",
            arrayOf<Any>(c.water, c.milk, c.beans, c.price),
        )
        return BuySuccess(c.displayName)
    }

    override fun fill(add: Resources) {
        helper.writableDatabase.execSQL(
            "UPDATE machine SET water = water + ?, milk = milk + ?, beans = beans + ?, cups = cups + ? WHERE id = 0",
            arrayOf<Any>(add.water, add.milk, add.beans, add.cups),
        )
    }

    override fun takeMoney(): Int {
        val taken = status().money
        helper.writableDatabase.execSQL("UPDATE machine SET money = 0 WHERE id = 0")
        return taken
    }
}
