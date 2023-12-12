package dev.argraur.fuzzy.impls

class TemperatureFuzzySet(val range: IntRange) :
    FunctionalFuzzySet<Double>(range.map { it.toDouble() }.toSet(), { x ->
        val relax = 10.0
        val a = range.first.toDouble()
        val b = range.last.toDouble()
        var result = 0.0
        if (x in a..b) {
            result = 1.0
        } else if (x < a && x > (a - relax)) {
            result = (x - (a - relax)) / relax
        } else if (x > b && x < (b + relax)) {
            result = ((b + relax) - x) / relax
        }
        result
    })
