package io.github.numq.blueprint.runtime.component.material

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ButtonVariant {
    @SerialName("BUTTON_FILLED")
    FILLED,

    @SerialName("BUTTON_OUTLINED")
    OUTLINED,

    @SerialName("BUTTON_TEXT")
    TEXT,

    @SerialName("BUTTON_ELEVATED")
    ELEVATED,

    @SerialName("BUTTON_FILLED_TONAL")
    FILLED_TONAL
}