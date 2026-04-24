package com.popov.calculator.domain.model

enum class Coffee(
    val displayName: String,
    val water: Int,
    val milk: Int,
    val beans: Int,
    val price: Int,
) {
    ESPRESSO("Espresso", 250, 0, 16, 4),
    LATTE("Latte", 350, 75, 20, 7),
    CAPPUCCINO("Cappuccino", 200, 100, 12, 6),
    UKRAINIAN("Ukrainian", 300, 100, 20, 8),
}
