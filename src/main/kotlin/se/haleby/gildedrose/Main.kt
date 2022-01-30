import se.haleby.gildedrose.domainmodel.GildedRose
import se.haleby.gildedrose.domainmodel.Item.*
import se.haleby.gildedrose.domainmodel.ItemName
import se.haleby.gildedrose.domainmodel.Quality.*
import se.haleby.gildedrose.domainmodel.SellingRequirement.SellIn
import se.haleby.gildedrose.GildedRose as LegacyGildedRose
import se.haleby.gildedrose.Item as LegacyItem

fun main() {
    runLegacyGildedRose()

    // New
    val items = listOf(
        RegularItem(ItemName("+5 Dexterity Vest"), SellIn(10), DegradesWithAgeQuality(20)),
        AgedBrie(SellIn(2), ImprovesWithAgeQuality(0)),
        RegularItem(ItemName("Elixir of the Mongoose"), SellIn(5), DegradesWithAgeQuality(7)),
        Sulfuras(ItemName("Sulfuras, Hand of Ragnaros")),
        BackstagePass(ItemName("Backstage passes to a TAFKAL80ETC concert"), SellIn(15), BackstagePassQuality(20)),
        Conjured(ItemName("Conjured Mana Cake"), SellIn(15), ConjuredQuality(20))
    )

    println("### NEW")

    (1..20).fold(GildedRose(items)) { gildedRose, day ->
        println("---- Day #$day ----")
        gildedRose.newDay()
            .also { updatedGlidedRose ->
                println(updatedGlidedRose.items.joinToString("\n"))
                println()
            }
    }

    println("### END")
}

private fun runLegacyGildedRose() {
    val items = mutableListOf(
        LegacyItem("+5 Dexterity Vest", 10, 20),
        LegacyItem("Aged Brie", 2, 0),
        LegacyItem("Elixir of the Mongoose", 5, 7),
        LegacyItem("Sulfuras, Hand of Ragnaros", 0, 80),
        LegacyItem("Backstage passes to a TAFKAL80ETC concert", 15, 20),
        LegacyItem("Conjured Mana Cake", 15, 20)
    )


    val gildedRose = LegacyGildedRose(items)
    gildedRose.runFor(20)
}


private fun LegacyGildedRose.runFor(days: Int) {
    for (day in 1..days) {
        println("---- Day #$day ----")
        updateQuality()
        print()
        println()
    }
}

private fun LegacyGildedRose.print() = items.forEach { item -> println(item) }