package io.github.numq.blueprint.runtime.component.layout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class LayoutArrangement {
    @SerialName("START")
    START,

    @SerialName("CENTER")
    CENTER,

    @SerialName("END")
    END,

    @SerialName("SPACE_BETWEEN")
    SPACE_BETWEEN,

    @SerialName("SPACE_AROUND")
    SPACE_AROUND,

    @SerialName("SPACE_EVENLY")
    SPACE_EVENLY
}