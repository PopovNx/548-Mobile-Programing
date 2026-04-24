package practice.coffeemachine

enum class Coffee(val water: Int, val milk: Int, val beans: Int, val price: Int) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6),
    UKRAINIAN(300, 100, 20, 8);

    companion object {
        fun fromInput(s: String) = s.trim().let { input ->
            entries.getOrNull(input.toIntOrNull()?.minus(1) ?: -1)
                ?: entries.firstOrNull { it.name.equals(input, ignoreCase = true) }
        }
    }
}