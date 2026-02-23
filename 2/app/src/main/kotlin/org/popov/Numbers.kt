package org.popov

import java.util.Locale
import java.util.Locale.getDefault
import kotlin.math.abs


fun main() {
    println("task: 1")

    println("Enter a number:")
    val num = readlnOrNull()?.toIntOrNull() ?: return

    val isEven = num % 2 == 0
    println(if (isEven) "Even" else "Odd")

    println("task: 2")
    val mul = if (isEven) 4 else 5
    println(num * mul)
    println("task: 3")
    println(-abs(num))

    println("task: 4")
    println("Enter a year:")
    val year = readlnOrNull()?.toIntOrNull() ?: return
    val century = (year + 99) / 100
    println(century)

    println("task: 5")
    println("enter array (space separate):")
    val arr = readlnOrNull()?.split(" ")?.map { it.toIntOrNull() ?: 0 } ?: return
    println(arr)
    val min = arr.minOrNull() ?: 0
    println(min)

    println("task: 6")
    val sorted = arr.sortedByDescending { it }
    println(sorted.take(2).reversed())

    println("task: 7")
    val positive = arr.filter { it > 0 }
    val negative = arr.filter { it < 0 }
    val sums = mutableListOf<Int>()
    if (!positive.isEmpty()) sums.add(positive.sum())
    if (!negative.isEmpty()) sums.add(negative.sum())
    println(sums)

    println("task: 8")
    println("enter string");
    val str = readlnOrNull() ?: return
    val vowelDb = "aeiou";
    println(str.lowercase().count { vowelDb.contains(it) })

    println("task: 9")
    val mid = str.length / 2
    val middles = if (str.length % 2 == 0) str.substring(mid - 1, mid + 1)
    else str.substring(mid, mid + 1)
    println(middles)

    println("task: 10")
    println("enter matrix size:")
    val matrixSize = readlnOrNull()?.toIntOrNull() ?: return
    repeat(matrixSize) { i ->
        println((1..matrixSize).joinToString(" ") { "${(i+1)*it}" })
    }

}
