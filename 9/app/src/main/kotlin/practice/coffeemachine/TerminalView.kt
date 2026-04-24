package practice.coffeemachine

class TerminalView(private val controller: Controller) : View {

    override fun start() {
        while (true) {
            when (prompt("Write action (buy, fill, take, remaining, exit)")) {
                "buy" -> handleBuy()
                "fill" -> handleFill()
                "take" -> controller.take()
                "remaining" -> controller.status()
                "exit" -> break
            }
            println()
        }
    }

    override fun display(response: Response) {
        println(response.message)
        if (response.message == "Current status") println(response.status)
    }

    private fun handleBuy() {
        controller.enterBuy()
        val menu = Coffee.entries.mapIndexed { i, c -> "${i + 1} - ${c.name.lowercase()}" }
            .joinToString(", ", postfix = ", back – to main menu")
        val input = prompt("What do you want to buy? $menu")
        if (input == "back") {
            controller.back()
            return
        }
        val coffee = Coffee.fromInput(input)
        if (coffee != null) controller.buy(Order(coffee))
        else controller.back()
    }

    private fun handleFill() {
        controller.enterFill()
        val resources = Resources(
            water = askInt("Write how many ml of water do you want to add"),
            milk = askInt("Write how many ml of milk do you want to add"),
            beans = askInt("Write how many grams of coffee beans do you want to add"),
            cups = askInt("Write how many disposable cups of coffee do you want to add"),
        )
        controller.fill(resources)
    }

    private fun prompt(msg: String): String {
        print("$msg:\n> ")
        return readln().trim()
    }

    private fun askInt(msg: String): Int {
        while (true) {
            val value = prompt(msg).toIntOrNull()
            if (value != null) return value
            println("Please provide a valid number")
        }
    }
}
