package se.haleby.gildedrose

import se.haleby.gildedrose.domainmodel.Item.*
import se.haleby.gildedrose.domainmodel.ItemName
import se.haleby.gildedrose.domainmodel.Quality.*
import se.haleby.gildedrose.domainmodel.SellingRequirement.SellIn
import se.haleby.gildedrose.domainmodel.GildedRose as NewShinyGildedRose

class GildedRose(val items: List<Item>) {


    fun updateQuality() {
        val currentShinyItems = items.map { item ->
            val (name, sellInDays, quality) = item
            when {
                name == "Aged Brie" -> AgedBrie(SellIn(sellInDays), ImprovesWithAgeQuality(quality))
                name.contains("Backstage") -> BackstagePass(ItemName(name), SellIn(sellInDays), BackstagePassQuality(quality))
                name.contains("Sulfuras") -> Sulfuras(ItemName(name))
                name.contains("Conjured") -> Conjured(ItemName(name), SellIn(sellInDays), ConjuredQuality(quality))
                else -> RegularItem(ItemName(name), SellIn(sellInDays), DegradesWithAgeQuality(quality))
            }
        }

        val newShinyItems = NewShinyGildedRose(currentShinyItems).updateQuality().items

        newShinyItems.forEachIndexed { i, newShinyItem ->
            items[i].quality = newShinyItem.quality.value
            if (newShinyItem.sellingRequirement is SellIn) {
                items[i].sellIn = (newShinyItem.sellingRequirement as SellIn).numberOfDays
            }
        }
    }
}