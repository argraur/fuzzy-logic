package dev.argraur.fuzzy.impls

import kotlin.math.abs
import kotlin.math.pow

class NumberFuzzySet(val n: Double)
    : FunctionalFuzzySet<Double>(setOf(n), { 1 / (1 + abs(it - 5).pow(5)) })
