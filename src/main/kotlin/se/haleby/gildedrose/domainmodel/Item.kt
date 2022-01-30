package se.haleby.gildedrose.domainmodel

import se.haleby.gildedrose.domainmodel.Quality.*
import se.haleby.gildedrose.domainmodel.Quality.Companion.BEST_QUALITY
import se.haleby.gildedrose.domainmodel.Quality.Companion.WORST_QUALITY
import se.haleby.gildedrose.domainmodel.SellingRequirement.NeverHasToBeSold
import se.haleby.gildedrose.domainmodel.SellingRequirement.SellIn

@JvmInline
value class ItemName(val value: String) {
    init {
        require(value.isNotEmpty()) {
            "Item name cannot be empty"
        }
    }
}

sealed interface Item {
    val name: ItemName
    val sellingRequirement: SellingRequirement
    val quality: Quality

    sealed interface UpdatableItem : Item {
        fun updateQuality(): Item
    }

    data class RegularItem(override val name: ItemName, override val sellingRequirement: SellIn, override val quality: DegradesWithAgeQuality) : UpdatableItem {
        override fun updateQuality(): Item {
            val numberOfDaysLeftToSell = sellingRequirement.decrease()
            val newQuality = quality.decrease(numberOfDaysLeftToSell)
            return copy(sellingRequirement = numberOfDaysLeftToSell, quality = newQuality)
        }

        override fun toString() = itemToString(name, sellingRequirement, quality)
    }

    data class AgedBrie(override val sellingRequirement: SellIn, override val quality: ImprovesWithAgeQuality) : UpdatableItem {
        override val name: ItemName = ItemName("Aged Brie")

        override fun updateQuality(): Item {
            val numberOfDaysLeftToSell = sellingRequirement.decrease()
            val newQuality = quality.increase(numberOfDaysLeftToSell)
            return copy(sellingRequirement = numberOfDaysLeftToSell, quality = newQuality)
        }

        override fun toString() = itemToString(name, sellingRequirement, quality)
    }

    data class BackstagePass(override val name: ItemName, override val sellingRequirement: SellIn, override val quality: BackstagePassQuality) : UpdatableItem {
        override fun updateQuality(): Item {
            val numberOfDaysLeftToSell = sellingRequirement.decrease()
            val newQuality = quality.update(numberOfDaysLeftToSell)
            return copy(sellingRequirement = numberOfDaysLeftToSell, quality = newQuality)
        }

        override fun toString() = itemToString(name, sellingRequirement, quality)
    }

    data class Conjured(override val name: ItemName, override val sellingRequirement: SellIn, override val quality: ConjuredQuality) : UpdatableItem {
        override fun updateQuality(): Item {
            val numberOfDaysLeftToSell = sellingRequirement.decrease()
            val newQuality = quality.decrease(numberOfDaysLeftToSell)
            return copy(sellingRequirement = numberOfDaysLeftToSell, quality = newQuality)
        }

        override fun toString() = itemToString(name, sellingRequirement, quality)
    }

    data class Sulfuras(override val name: ItemName) : Item {
        override val sellingRequirement = NeverHasToBeSold
        override val quality: Quality = SulfurasQuality
        override fun toString() = itemToString(name, sellingRequirement, quality)
    }
}

private fun itemToString(name: ItemName, sellingRequirement: SellingRequirement, quality: Quality) = "${name.value} / sell in ${if (sellingRequirement is SellIn) sellingRequirement.numberOfDays else sellingRequirement::class.simpleName} / quality of ${quality.value}"

private fun SellIn.decrease() = copy(numberOfDays = numberOfDays.dec())
private fun SellIn.isOverdue() = numberOfDays < 0

private fun twiceAsFastIfOverdue(sellIn: SellIn) = if (sellIn.isOverdue()) 2 else 1
private fun DegradesWithAgeQuality.decrease(sellIn: SellIn) = copy(value = value.minus(twiceAsFastIfOverdue(sellIn)).coerceAtLeast(WORST_QUALITY))
private fun ConjuredQuality.decrease(sellIn: SellIn) = copy(value = value.minus(twiceAsFastIfOverdue(sellIn).times(2)).coerceAtLeast(WORST_QUALITY))
private fun ImprovesWithAgeQuality.increase(sellIn: SellIn) = copy(value = value.plus(twiceAsFastIfOverdue(sellIn)).coerceAtMost(BEST_QUALITY))
private fun BackstagePassQuality.update(sellIn: SellIn): BackstagePassQuality {
    val numberOfDaysLeft = sellIn.numberOfDays
    val newQualityValue = when {
        numberOfDaysLeft < 0 -> 0
        numberOfDaysLeft < 5 -> value.plus(3)
        numberOfDaysLeft < 10 -> value.plus(2)
        else -> value.inc()
    }
    return copy(value = newQualityValue.coerceAtMost(BEST_QUALITY))
}