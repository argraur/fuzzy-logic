package dev.argraur.fuzzy

import dev.argraur.fuzzy.impls.NumberFuzzySet
import dev.argraur.fuzzy.impls.TemperatureFuzzySet
import org.jetbrains.letsPlot.geom.geomLine
import org.jetbrains.letsPlot.intern.toSpec
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.scale.scaleXContinuous
import org.jetbrains.letsPlot.scale.scaleYContinuous
import java.io.FileOutputStream

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

    val warm = mapOf(
        "Температура" to (-30..110).toList(),
        "Принадлежность" to (-30..110).map { warmTemperatureFuzzySet.contains(it.toDouble()) },
        "set" to List(141) { "Warm" }
    )

    val freezing = mapOf(
        "Температура" to (-30..110).toList(),
        "Принадлежность" to (-30..110).map { freezingTemperatureFuzzySet.contains(it.toDouble()) },
        "set" to List(141) { "Freezing" }
    )

    val cold = mapOf(
        "Температура" to (-30..110).toList(),
        "Принадлежность" to (-30..110).map { coldTemperatureFuzzySet.contains(it.toDouble()) },
        "set" to List(141) { "Cold" }
    )

    val hot = mapOf(
        "Температура" to (-30..110).toList(),
        "Принадлежность" to (-30..110).map { hotTemperatureFuzzySet.contains(it.toDouble()) },
        "set" to List(141) { "Hot" }
    )

    val boiling = mapOf(
        "Температура" to (-30..110).toList(),
        "Принадлежность" to (-30..110).map { boilingTemperatureFuzzySet.contains(it.toDouble()) },
        "set" to List(141) { "Boiling" }
    )

    val p = letsPlot() +
            geomLine(data = freezing, size = 1.0) { x = "Температура"; y = "Принадлежность"; color = "set" } +
            geomLine(data = cold, size = 1.0) { x = "Температура"; y = "Принадлежность"; color = "set" } +
            geomLine(data = warm, size = 1.0) { x = "Температура"; y = "Принадлежность"; color = "set" } +
            geomLine(data = hot, size = 1.0) { x = "Температура"; y = "Принадлежность"; color = "set" } +
            geomLine(data = boiling, size = 1.0) { x = "Температура"; y = "Принадлежность"; color = "set" } +
            ggtitle("Нечёткое множество, температура") +
            scaleXContinuous(breaks = (-30..110 step 10).toList()) +
            scaleYContinuous(limits = 0.0 to 1.0)

    val image = org.jetbrains.letsPlot.core.plot.export.PlotImageExport.buildImageFromRawSpecs(
        plotSpec = p.toSpec(),
        format = org.jetbrains.letsPlot.core.plot.export.PlotImageExport.Format.PNG,
        scalingFactor = 2.0,
        targetDPI = Double.NaN
    )

    val fos = FileOutputStream("temperature.png")
    fos.write(image.bytes)
    fos.close()

    val xVal = mutableListOf<Double>()
    var xx = 8.0
    while (xx > 2.0) {
        xVal.add(xx)
        xx -= 0.01
    }

    val five = mapOf(
        "Значение" to xVal,
        "Принадлежность" to xVal.map { numberFuzzySet.contains(it.toDouble()) },
        "set" to List(xVal.size) { "NumberFuzzySet(5)" }
    )

    val fiveVery = mapOf(
        "Значение" to xVal,
        "Принадлежность" to xVal.map { numberFuzzySet.very().contains(it.toDouble()) },
        "set" to List(xVal.size) { "NumberFuzzySet(5).very()" }
    )

    val fiveMoreOrLess = mapOf(
        "Значение" to xVal,
        "Принадлежность" to xVal.map { numberFuzzySet.moreOrLess().contains(it.toDouble()) },
        "set" to List(xVal.size) { "NumberFuzzySet(5).moreOrLess()" }
    )

    val notFive = mapOf(
        "Значение" to xVal,
        "Принадлежность" to xVal.map { (!numberFuzzySet).contains(it.toDouble()) },
        "set" to List(xVal.size) { "NOT(NumberFuzzySet(5))" }
    )

    val p1 = letsPlot() +
            geomLine(data = five, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            geomLine(data = fiveVery, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            geomLine(data = fiveMoreOrLess, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            geomLine(data = notFive, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            ggtitle("Нечёткое множество, число 5") +
            scaleXContinuous(breaks = (2..8).toList()) +
            scaleYContinuous(limits = 0.0 to 1.0)

    val image1 = org.jetbrains.letsPlot.core.plot.export.PlotImageExport.buildImageFromRawSpecs(
        plotSpec = p1.toSpec(),
        format = org.jetbrains.letsPlot.core.plot.export.PlotImageExport.Format.PNG,
        scalingFactor = 3.0,
        targetDPI = Double.NaN
    )

    val fos1 = FileOutputStream("number.png")
    fos1.write(image1.bytes)
    fos1.close()
}
