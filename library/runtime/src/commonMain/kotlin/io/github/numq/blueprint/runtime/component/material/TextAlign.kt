package io.github.numq.blueprint.runtime.component.material

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TextAlign {
    @SerialName("TEXT_START")
    START,

    @SerialName("TEXT_CENTER")
    CENTER,

    @SerialName("TEXT_END")
    END
}