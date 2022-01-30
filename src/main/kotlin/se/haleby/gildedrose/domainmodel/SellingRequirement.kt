package se.haleby.gildedrose.domainmodel

typealias NumberOfDays = Int

sealed interface SellingRequirement {
    data class SellIn(val numberOfDays: NumberOfDays) : SellingRequirement
    object NeverHasToBeSold : SellingRequirement
}