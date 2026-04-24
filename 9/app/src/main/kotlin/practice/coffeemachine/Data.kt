package practice.coffeemachine

data class Order(val coffee: Coffee)

data class Resources(
    val water: Int = 0,
    val milk: Int = 0,
    val beans: Int = 0,
    val cups: Int = 0,
)

data class Response(val message: String, val status: String)
