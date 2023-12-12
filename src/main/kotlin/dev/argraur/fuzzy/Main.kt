package dev.argraur.fuzzy

import dev.argraur.fuzzy.impls.NumberFuzzySet
import dev.argraur.fuzzy.impls.TemperatureFuzzySet

fun main(args: Array<String>) {
    val freezingTemperatureFuzzySet = TemperatureFuzzySet(-100..-5)
    val coldTemperatureFuzzySet = TemperatureFuzzySet(-10..5)
    val warmTemperatureFuzzySet = TemperatureFuzzySet(15..25)
    val hotTemperatureFuzzySet = TemperatureFuzzySet(30..50)
    val unbearablyHotTemperatureFuzzySet = TemperatureFuzzySet(60..90)
    val boilingTemperatureFuzzySet = TemperatureFuzzySet(100..110)

    for (i in 0..100 step 2) {
        println("t = $i")
        println("Cold: ${coldTemperatureFuzzySet.contains(i.toDouble()) * 100}%")
        println("Warm: ${warmTemperatureFuzzySet.contains(i.toDouble()) * 100}%")
        println("Hot: ${hotTemperatureFuzzySet.contains(i.toDouble()) * 100}%")
        println("Unbearably hot: ${unbearablyHotTemperatureFuzzySet.contains(i.toDouble()) * 100}%")
        println("Boiling: ${boilingTemperatureFuzzySet.contains(i.toDouble()) * 100}%")
    }

    val or = warmTemperatureFuzzySet.or(hotTemperatureFuzzySet)

    for (i in 10..60 step 2) {
        println("t = $i")
        println("Warm OR Hot: ${or.contains(i.toDouble()) * 100}%")
    }

    val and = freezingTemperatureFuzzySet.and(coldTemperatureFuzzySet)
    for (i in -20..5) {
        println("t = $i")
        println("Freezing and Cold: ${and.contains(i.toDouble())* 100}%")
    }

    val not = !warmTemperatureFuzzySet
    for (i in 0..100) {
        println("is NOT warm at $i*C: ${not.contains(i.toDouble()) * 100}%")
    }

    val sum = warmTemperatureFuzzySet + hotTemperatureFuzzySet
    for (i in 0..100) {
        println("warm + hot at $i*C: ${sum.contains(i.toDouble()) * 100}%")
    }

    val mercuryFreezing = TemperatureFuzzySet(-38..-37)
    println("Freezing contains mercury freezing? ${freezingTemperatureFuzzySet.containsAll(mercuryFreezing)}")

    val newColdTemperatureFuzzySet = TemperatureFuzzySet(-10..5)
    println("Copy of cold set == cold set? ${newColdTemperatureFuzzySet.equals(coldTemperatureFuzzySet)}")

    val numberFuzzySet = NumberFuzzySet(5.0)
    for (i in 0..10)
        println("Is $i - 5? ${numberFuzzySet.contains(i.toDouble()) * 100}%")

    for (i in 0..10)
        println("Is $i very close to 5? ${numberFuzzySet.very().contains(i.toDouble()) * 100}%")

    for (i in 0..10)
        println("Is $i more or less 5? ${numberFuzzySet.moreOrLess().contains(i.toDouble()) * 100}%")
}
