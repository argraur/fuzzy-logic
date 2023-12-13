package dev.argraur.fuzzy.impls

class AgeGroupFuzzySet(ageMembershipFn: (Int) -> Double) : FunctionalFuzzySet<Int>(
    (0..100).toSet(),
    ageMembershipFn
)
