package org.popov

enum class Move { ROCK, PAPER, SCISSORS }

fun main() {
    println("Rock Paper Scissors!")

    val computer = Move.entries.random()
    println("Computer made its choice")

    var player: Move? = null
    while (player == null) {
        println("Enter your move (1: rock, 2: paper, 3: scissors):")
        player = when (readlnOrNull()) {
            "1" -> Move.ROCK
            "2" -> Move.PAPER
            "3" -> Move.SCISSORS
            else -> {
                println("Invalid input. Please enter 1, 2, or 3.")
                null
            }
        }
    }

    println("You chose $player, computer chose $computer")
    val wins = mapOf(
        Move.ROCK to Move.SCISSORS,
        Move.PAPER to Move.ROCK,
        Move.SCISSORS to Move.PAPER
    )

    println(when {
        player == computer -> "It's a draw!"
        wins[player] == computer -> "You win!"
        else -> "Computer wins!"
    })
}