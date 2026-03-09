package org.popov

class Cinema(val rows: Int, val cols: Int) {
    private val totalSeats: Int = rows * cols
    private val isDiscountable: Boolean = totalSeats > 60;

    private var basePrice: Int = 10;
    private var discountedPrice: Int = 8;

    private var soldSeats = emptySet<Pair<Int, Int>>()

    fun getSeatPrice(row: Int): Int {
        return if (!isDiscountable || row <= rows / 2) basePrice
        else discountedPrice
    }

    fun checkBounds(seat: Pair<Int, Int>): Boolean {
        return seat.first in 1..rows && seat.second in 1..cols
    }

    fun isSold(seat: Pair<Int, Int>): Boolean {
        return seat in soldSeats
    }

    fun buySeat(seat: Pair<Int, Int>): Boolean {
        if (!checkBounds(seat) || isSold(seat)) return false
        soldSeats += seat
        return true
    }

    data class Statistics(
        val totalTicketsSold: Int,
        val percentageSold: Double,
        val currentIncome: Int,
        val totalIncome: Int
    )

    fun getStatistics(): Statistics {
        val totalTicketsSold = soldSeats.size
        val percentageSold = if (totalSeats > 0) totalTicketsSold.toDouble() / totalSeats * 100 else 0.0
        val currentIncome = soldSeats.sumOf { getSeatPrice(it.first) }
        val totalIncome = (1..rows).sumOf { row ->
            (1..cols).sumOf { _ ->
                getSeatPrice(row)
            }
        }
        return Statistics(totalTicketsSold, percentageSold, currentIncome, totalIncome)

    }

    fun render() {
        val colWidth = maxOf(rows, cols).toString().length + 2
        print(" ".repeat(colWidth))
        for (col in 1..cols) {
            print("%${colWidth}d".format(col))
        }
        println()
        for (row in 1..rows) {
            print("%${colWidth}d".format(row))
            for (col in 1..cols) {
                val seat = if (isSold(row to col)) "B" else "S"
                print("%${colWidth}s".format(seat))
            }
            println()
        }
    }
}

class CinemaUi(val cinema: Cinema) {
    companion object {
        private fun readInteger(prompt: String, range: IntRange? = null): Int {
            while (true) {
                println(prompt)
                print("> ")
                val input = readlnOrNull()
                val number = input?.toIntOrNull()
                if (number != null && (range == null || number in range)) {
                    return number
                }
                if (range != null) {
                    println("Please enter a number between ${range.first} and ${range.last}.")
                } else {
                    println("Please enter a valid integer.")
                }
            }
        }

        private fun initializeCinema(): Cinema {
            val rows = readInteger("Enter the number of rows: ", 1..100)
            val cols = readInteger("Enter the number of seats in each row: ", 1..100)
            return Cinema(rows, cols)
        }

        fun initializeUi(): CinemaUi {
            return CinemaUi(initializeCinema())
        }
    }

    private fun showSeats() {
        cinema.render()
    }

    private fun buyTicket() {
        while (true) {
            val row = readInteger("Enter a row number: ", 1..cinema.rows)
            val col = readInteger("Enter a seat number in that row: ", 1..cinema.cols)
            if (!cinema.checkBounds(row to col)) {
                println("Wrong input!")
                continue
            }
            if (cinema.isSold(row to col)) {
                println("That ticket has already been purchased!")
                continue
            }

            if (cinema.buySeat(row to col)) {
                println("This is your ticket now! Price: $${cinema.getSeatPrice(row)}")
                break
            } else {
                throw IllegalStateException("No!")
            }
        }
    }

    private fun showStatistics() {
        val stats = cinema.getStatistics()
        println("Total tickets sold: ${stats.totalTicketsSold}")
        println("Percentage of tickets sold: ${"%.2f".format(stats.percentageSold)}%")
        println("Current income: $${stats.currentIncome}")
        println("Total income: $${stats.totalIncome}")
        println()
    }

    private fun showMenu(): Boolean {
        println("1. Show seats")
        println("2. Buy ticket")
        println("3. Show statistics")
        println("4. Exit")
        val choice = readInteger("Enter your choice: ", 0..3)
        when (choice) {
            1 -> showSeats()
            2 -> buyTicket()
            3 -> showStatistics()
            0 -> return false
        }
        return true
    }

    fun run() {
        while (true) {
            if (!showMenu()) break
        }
    }

}


fun main() {
    val cinema = CinemaUi.initializeUi();
    cinema.run()
}