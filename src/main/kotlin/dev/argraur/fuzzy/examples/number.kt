package dev.argraur.fuzzy.examples

import dev.argraur.fuzzy.impls.NumberFuzzySet
import org.jetbrains.letsPlot.core.plot.export.PlotImageExport
import org.jetbrains.letsPlot.geom.geomLine
import org.jetbrains.letsPlot.intern.toSpec
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.scale.scaleXContinuous
import org.jetbrains.letsPlot.scale.scaleYContinuous
import java.io.FileOutputStream

fun main() {
    val numberFuzzySet = NumberFuzzySet(5.0)
    for (i in 0..10)
        println("Is $i - 5? ${numberFuzzySet.contains(i.toDouble()) * 100}%")

    for (i in 0..10)
        println("Is $i very close to 5? ${numberFuzzySet.very().contains(i.toDouble()) * 100}%")

    for (i in 0..10)
        println("Is $i more or less 5? ${numberFuzzySet.moreOrLess().contains(i.toDouble()) * 100}%")

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

    val p = letsPlot() +
            geomLine(data = five, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            geomLine(data = fiveVery, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            geomLine(data = fiveMoreOrLess, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            geomLine(data = notFive, size = 1.0) { x = "Значение"; y = "Принадлежность"; color = "set" } +
            ggtitle("Нечёткое множество, число 5") +
            scaleXContinuous(breaks = (2..8).toList()) +
            scaleYContinuous(limits = 0.0 to 1.0)

    val image = PlotImageExport.buildImageFromRawSpecs(
        plotSpec = p.toSpec(),
        format = PlotImageExport.Format.PNG,
        scalingFactor = 3.0,
        targetDPI = Double.NaN
    )

    val fos = FileOutputStream("number.png")
    fos.write(image.bytes)
    fos.close()
}
