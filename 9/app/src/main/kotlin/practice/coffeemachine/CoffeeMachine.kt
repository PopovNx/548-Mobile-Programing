package practice.coffeemachine

class CoffeeMachine {
    enum class State { MAIN, BUYING, FILLING }

    private var resources = Resources(water = 400, milk = 540, beans = 120, cups = 9)
    private var money = 550

    var state: State = State.MAIN
        private set

    private val statusText: String
        get() = """
            |Water: ${resources.water} ml
            |Milk: ${resources.milk} ml
            |Beans: ${resources.beans} g
            |Cups: ${resources.cups}
            |Money: $money UAH
        """.trimMargin()

    fun enterBuy(): Response {
        state = State.BUYING
        return Response("Choose a coffee", statusText)
    }

    fun enterFill(): Response {
        state = State.FILLING
        return Response("Enter amounts to add", statusText)
    }

    fun back(): Response {
        state = State.MAIN
        return Response("Back to main", statusText)
    }

    fun buy(order: Order): Response {
        val c = order.coffee
        val lacking = listOf(
            "water" to (c.water - resources.water),
            "milk" to (c.milk - resources.milk),
            "beans" to (c.beans - resources.beans),
            "cups" to (1 - resources.cups),
        ).firstOrNull { it.second > 0 }

        val msg = if (lacking != null) {
            "Sorry, not enough ${lacking.first}!"
        } else {
            resources = Resources(
                water = resources.water - c.water,
                milk = resources.milk - c.milk,
                beans = resources.beans - c.beans,
                cups = resources.cups - 1,
            )
            money += c.price
            "Enjoy your ${c.name.lowercase()}!"
        }
        state = State.MAIN
        return Response(msg, statusText)
    }

    fun fill(add: Resources): Response {
        resources = Resources(
            water = resources.water + add.water,
            milk = resources.milk + add.milk,
            beans = resources.beans + add.beans,
            cups = resources.cups + add.cups,
        )
        state = State.MAIN
        return Response("Resources filled", statusText)
    }

    fun take(): Response {
        val taken = money
        money = 0
        return Response("I gave you $taken UAH", statusText)
    }

    fun status(): Response = Response("Current status", statusText)
}
