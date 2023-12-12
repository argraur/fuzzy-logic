package dev.argraur.fuzzy.impls

import dev.argraur.fuzzy.AbstractFuzzySet
import dev.argraur.fuzzy.FuzzySet
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

open class FunctionalFuzzySet<T>(
    private val elements: Set<T>,
    val membership: (T) -> Double
): AbstractFuzzySet<T>() {
    override fun elements(): Set<T> = elements

    override fun contains(element: T): Double = membership(element)

    override fun containsAll(set: FuzzySet<T>): Boolean {
        if (set is FunctionalFuzzySet<T>) {
            for (x in set.elements()) {
                if (membership(x) < set.membership(x)) {
                    return false
                }
            }
            return true
        }
        throw IllegalArgumentException()
    }

    override fun cross(set: FuzzySet<T>): FuzzySet<T> {
        if (set is FunctionalFuzzySet)
            return FunctionalFuzzySet(elements + set.elements) { min(membership(it), set.membership(it)) }
        throw IllegalArgumentException("Can only work with another FunctionalFuzzySet")
    }

    override fun union(set: FuzzySet<T>): FuzzySet<T> {
        if (set is FunctionalFuzzySet)
            return FunctionalFuzzySet(elements.union(set.elements)) { max(membership(it), set.membership(it))}
        throw IllegalArgumentException("Can only work with another FunctionalFuzzySet")
    }

    override fun multiply(set: FuzzySet<T>): FuzzySet<T> {
        if (set is FunctionalFuzzySet)
            return FunctionalFuzzySet(elements + set.elements) { membership(it) * set.membership(it) }
        throw IllegalArgumentException("Can only work with another FunctionalFuzzySet")
    }

    override fun sum(set: FuzzySet<T>): FuzzySet<T> {
        if (set is FunctionalFuzzySet)
            return FunctionalFuzzySet(elements + set.elements) { membership(it) + set.membership(it) - membership(it) * set.membership(it) }
        throw IllegalArgumentException("Can only work with another FunctionalFuzzySet")
    }

    override fun negate(): FuzzySet<T> =
        FunctionalFuzzySet(elements) { 1 - membership(it) }

    override fun very(): FuzzySet<T> =
        FunctionalFuzzySet(elements) { membership(it) * membership(it) }

    override fun moreOrLess(): FuzzySet<T> =
        FunctionalFuzzySet(elements) { sqrt(membership(it)) }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false

        if (other is FunctionalFuzzySet<*>) {
            try {
                val o = other as FunctionalFuzzySet<T>
                if (elements() == other.elements()) {
                    for (i in this) {
                        if (other.membership(i) != membership(i))
                            return false
                    }
                    return true
                }
            } catch (e: ClassCastException) {
                return false
            }
        }

        return false
    }
}
