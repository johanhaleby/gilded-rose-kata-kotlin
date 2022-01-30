package se.haleby.gildedrose.domainmodel

typealias NumberOfDays = Int

sealed interface SellingRequirement {
    data class SellIn(val value: NumberOfDays) : SellingRequirement
    object NeverHasToBeSold : SellingRequirement
}