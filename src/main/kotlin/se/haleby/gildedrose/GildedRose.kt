package se.haleby.gildedrose

import se.haleby.gildedrose.domainmodel.Item.*
import se.haleby.gildedrose.domainmodel.ItemName
import se.haleby.gildedrose.domainmodel.Quality.*
import se.haleby.gildedrose.domainmodel.SellingRequirement.SellIn
import se.haleby.gildedrose.domainmodel.GildedRose as DomainModelGildedRose

class GildedRose(val items: List<Item>) {


    fun updateQuality() {
        val currentDomainModelItems = items.map { item ->
            val (name, sellInDays, quality) = item
            when {
                name == "Aged Brie" -> AgedBrie(SellIn(sellInDays), ImprovesWithAgeQuality(quality))
                name.contains("Backstage") -> BackstagePass(ItemName(name), SellIn(sellInDays), BackstagePassQuality(quality))
                name.contains("Sulfuras") -> Sulfuras(ItemName(name))
                name.contains("Conjured") -> Conjured(ItemName(name), SellIn(sellInDays), ConjuredQuality(quality))
                else -> RegularItem(ItemName(name), SellIn(sellInDays), DegradesWithAgeQuality(quality))
            }
        }

        val newDomainModelItems = DomainModelGildedRose(currentDomainModelItems).updateQuality().items

        newDomainModelItems.forEachIndexed { i, newDomainModelItem ->
            items[i].quality = newDomainModelItem.quality.value
            if (newDomainModelItem.sellingRequirement is SellIn) {
                items[i].sellIn = (newDomainModelItem.sellingRequirement as SellIn).numberOfDays
            }
        }
    }
}