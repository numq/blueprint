package io.github.numq.blueprint.runtime.component.material

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class IconSize {
    @SerialName("ICON_SMALL")
    SMALL,

    @SerialName("ICON_MEDIUM")
    MEDIUM,

    @SerialName("ICON_LARGE")
    LARGE
}