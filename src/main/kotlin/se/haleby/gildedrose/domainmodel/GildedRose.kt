package se.haleby.gildedrose.domainmodel

data class GildedRose(val items: List<Item>) {
    fun updateQuality(): GildedRose = copy(items = items.map { item ->
        if (item is Item.UpdatableItem) item.updateQuality() else item
    })
}