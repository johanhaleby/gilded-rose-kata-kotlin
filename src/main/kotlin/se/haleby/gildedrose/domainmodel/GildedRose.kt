package se.haleby.gildedrose.domainmodel

data class GildedRose(val items: List<Item>) {
    fun newDay(): GildedRose = copy(items = items.map(Item::newDay))
}