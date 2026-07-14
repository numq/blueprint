package io.github.numq.blueprint.runtime.component.material

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardVariant {
    @SerialName("CARD_ELEVATED")
    ELEVATED,

    @SerialName("CARD_FILLED")
    FILLED,

    @SerialName("CARD_OUTLINED")
    OUTLINED
}