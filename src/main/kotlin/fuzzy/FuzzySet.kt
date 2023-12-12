package dev.argraur.fuzzy

interface FuzzySet<T> {
    fun elements(): Set<T>
    fun contains(element: T): Double
    fun containsAll(set: FuzzySet<T>): Boolean
    fun cross(set: FuzzySet<T>): FuzzySet<T>
    fun union(set: FuzzySet<T>): FuzzySet<T>
    fun multiply(set: FuzzySet<T>): FuzzySet<T>
    fun sum(set: FuzzySet<T>): FuzzySet<T>
    fun negate(): FuzzySet<T>
    fun and(set: FuzzySet<T>): FuzzySet<T>
    fun or(set: FuzzySet<T>): FuzzySet<T>
    fun very(): FuzzySet<T>
    fun moreOrLess(): FuzzySet<T>
}
