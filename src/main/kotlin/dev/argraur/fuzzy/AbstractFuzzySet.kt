package dev.argraur.fuzzy

abstract class AbstractFuzzySet<T> : FuzzySet<T> {
    operator fun plus(other: FuzzySet<T>) = sum(other)
    operator fun unaryMinus() = negate()
    operator fun not() = negate()
    operator fun times(other: FuzzySet<T>) = multiply(other)
    operator fun iterator(): Iterator<T> = elements().iterator()
    override fun and(other: FuzzySet<T>) = cross(other)
    override fun or(other: FuzzySet<T>) = union(other)
}
