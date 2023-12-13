package dev.argraur.fuzzy.examples

import dev.argraur.fuzzy.impls.AgeGroupFuzzySet
import org.jetbrains.letsPlot.core.plot.export.PlotImageExport
import org.jetbrains.letsPlot.geom.geomLine
import org.jetbrains.letsPlot.intern.toSpec
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.scale.scaleXContinuous
import org.jetbrains.letsPlot.scale.scaleYContinuous
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.pow

fun main() {
    val young = AgeGroupFuzzySet { age ->
        max(0.0, 1 - (age / 30.0).pow(2))
    }

    val middleAged = AgeGroupFuzzySet { age ->
        max(0.0, 1 - ((age - 50.0) / 30.0).pow(2))
    }

    val old = AgeGroupFuzzySet { age ->
        if (age > 50)
            max(0.0, ((age - 50) / 50.0).pow(2))
        else 0.0
    }

    for (i in 0..100) {
        println("Age: $i, young: ${young.contains(i) * 100}%, middleAged: ${middleAged.contains(i) * 100}%, old: ${old.contains(i) * 100}%")
    }

    val youngData = mapOf(
        "Возраст" to (0..100).toList(),
        "Принадлежность" to (0..100).map { young.contains(it) },
        "Множество" to List((0..100).toList().size) { "Молодые" }
    )

    val middleAgedData = mapOf(
        "Возраст" to (0..100).toList(),
        "Принадлежность" to (0..100).map { middleAged.contains(it) },
        "Множество" to List((0..100).toList().size) { "Среднего возраста" }
    )

    val oldData = mapOf(
        "Возраст" to (0..100).toList(),
        "Принадлежность" to (0..100).map { old.contains(it) },
        "Множество" to List((0..100).toList().size) { "Пожилые" }
    )

    val p = letsPlot() +
            geomLine(data = youngData, size = 1.0) { x = "Возраст"; y = "Принадлежность"; color = "Множество" } +
            geomLine(data = middleAgedData, size = 1.0) { x = "Возраст"; y = "Принадлежность"; color = "Множество" } +
            geomLine(data = oldData, size = 1.0) { x = "Возраст"; y = "Принадлежность"; color = "Множество" } +
            ggtitle("Нечёткое множество, возрастные группы") +
            scaleXContinuous(breaks = (0..100 step 3).toList()) +
            scaleYContinuous(limits = 0.0 to 1.0)

    val image = PlotImageExport.buildImageFromRawSpecs(
        p.toSpec(),
        PlotImageExport.Format.PNG,
        2.0,
        Double.NaN
    )

    val fos = FileOutputStream("age_groups.png")
    fos.write(image.bytes)
    fos.close()
}
