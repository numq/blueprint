package io.github.numq.blueprint.runtime.modifier

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface NodeModifier {
    @Serializable
    @SerialName("padding")
    data class Padding(val start: Float = 0f, val top: Float = 0f, val end: Float = 0f, val bottom: Float = 0f) :
        NodeModifier

    @Serializable
    @SerialName("width")
    data class Width(val width: Float = Float.NaN) : NodeModifier

    @Serializable
    @SerialName("height")
    data class Height(val height: Float = Float.NaN) : NodeModifier

    @Serializable
    @SerialName("size")
    data class Size(val width: Float = Float.NaN, val height: Float = Float.NaN) : NodeModifier

    @Serializable
    @SerialName("background")
    data class Background(val colorHex: String) : NodeModifier

    @Serializable
    @SerialName("elevation")
    data class Elevation(val elevation: Float = 0f) : NodeModifier

    @Serializable
    @SerialName("corner_radius")
    data class CornerRadius(val radius: Float = 0f) : NodeModifier

    @Serializable
    @SerialName("alpha")
    data class Alpha(val alpha: Float = 1f) : NodeModifier

    @Serializable
    @SerialName("weight")
    data class Weight(val weight: Float = 1f) : NodeModifier

    @Serializable
    @SerialName("fill_max_width")
    data class FillMaxWidth(val fraction: Float = 1f) : NodeModifier

    @Serializable
    @SerialName("fill_max_height")
    data class FillMaxHeight(val fraction: Float = 1f) : NodeModifier

    @Serializable
    @SerialName("fill_max_size")
    data class FillMaxSize(val fraction: Float = 1f) : NodeModifier
}