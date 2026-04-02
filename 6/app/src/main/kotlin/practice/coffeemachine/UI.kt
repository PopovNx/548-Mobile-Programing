package practice.coffeemachine

object UI {
    fun prompt(msg: String): String {
        print("$msg:\n> "); return readln().trim()
    }

    fun say(msg: String): Unit = println(msg)

    fun untilIntoInt(getValue: () -> String): Int {
        while (true) {
            val value = getValue().toIntOrNull()
            if (value != null) return value
            else say("Please provide a valid number")
        }
    }

    fun handleBuy(machine: CoffeeMachine) {
        val menu = Coffee.entries.mapIndexed { i, c -> "${i + 1} - ${c.name.lowercase()}" }
            .joinToString(
                ", ",
                postfix = ", back – to main menu"
            )
        prompt("What do you want to buy? $menu")
            .let { Coffee.fromInput(it) }
            ?.let { say(machine.buy(it)) }
    }

    fun handleFill(machine: CoffeeMachine) = machine.fill(
        water = untilIntoInt { prompt("Write how many ml of water do you want to add") },
        milk = untilIntoInt { prompt("Write how many ml of milk do you want to add") },
        beans = untilIntoInt { prompt("Write how many grams of coffee beans do you want to add") },
        cups = untilIntoInt { prompt("Write how many disposable cups of coffee do you want to add") }
    )
}