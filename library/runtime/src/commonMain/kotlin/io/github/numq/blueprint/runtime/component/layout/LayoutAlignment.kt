package io.github.numq.blueprint.runtime.component.layout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class LayoutAlignment {
    @SerialName("ALIGN_START")
    ALIGN_START,

    @SerialName("ALIGN_CENTER")
    ALIGN_CENTER,

    @SerialName("ALIGN_END")
    ALIGN_END,

    @SerialName("ALIGN_STRETCH")
    ALIGN_STRETCH
}