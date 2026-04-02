package practice.coffeemachine

class CoffeeMachine(
    private var water: Int = 400,
    private var milk: Int = 540,
    private var beans: Int = 120,
    private var cups: Int = 9,
    private var money: Int = 550
) {
    val status
        get() = """
        |The coffee machine has:
        |$water of water
        |$milk of milk
        |$beans of coffee beans
        |$cups of disposable cups
        |$money of uah
    """.trimMargin()

    fun buy(coffee: Coffee): String {
        val lacking = listOf(
            "water" to (coffee.water - water),
            "milk" to (coffee.milk - milk),
            "coffee beans" to (coffee.beans - beans),
            "cups" to (1 - cups)
        ).firstOrNull { it.second > 0 }

        return if (lacking != null) {
            "Sorry, not enough ${lacking.first}!"
        } else {
            water -= coffee.water
            milk -= coffee.milk
            beans -= coffee.beans
            cups--
            money += coffee.price
            "I have enough resources, making you a coffee!"
        }
    }

    fun fill(water: Int, milk: Int, beans: Int, cups: Int) {
        this.water += water
        this.milk += milk
        this.beans += beans
        this.cups += cups
    }

    fun take(): Int = money.also { money = 0 }
}