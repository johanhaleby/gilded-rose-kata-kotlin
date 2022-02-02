package se.haleby.gildedrose.domainmodel

typealias QualityValue = Int

// Quality
sealed interface Quality {
    val value: QualityValue

    data class DegradesWithAgeQuality(override val value: QualityValue) : Quality {
        init {
            validateRegularQualityRequirements(value)
        }
    }

    data class ImprovesWithAgeQuality(override val value: QualityValue) : Quality {
        init {
            validateRegularQualityRequirements(value)
        }
    }

    // We explicitly model this as its own data class since we want to make it impossible
    // to construct Conjured items with a "DegradesWithAgeQuality" with the wrong "degrading factor".
    data class ConjuredQuality(override val value: QualityValue) : Quality {
        init {
            validateRegularQualityRequirements(value)
        }
    }


    data class BackstagePassQuality(override val value: QualityValue) : Quality {
        init {
            validateRegularQualityRequirements(value)
        }
    }


    object SulfurasQuality : Quality {
        override val value = 80
    }

    companion object {
        internal const val BEST_QUALITY = 50
        internal const val WORST_QUALITY = 0
        private fun validateRegularQualityRequirements(quality: QualityValue) = require(quality in WORST_QUALITY..BEST_QUALITY) {
            "Quality must have a value between 0 and 50, was $quality"
        }
    }
}