package practice.coffeemachine

fun main() {
    val machine = CoffeeMachine()

    while (true) {
        when (UI.prompt("Write action (buy, fill, take, remaining, exit)")) {
            "buy" -> UI.handleBuy(machine)
            "fill" -> UI.handleFill(machine)
            "take" -> UI.say("I gave you ${machine.take()}")
            "remaining" -> UI.say(machine.status)
            "exit" -> return
        }
        println()
    }
}

