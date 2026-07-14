package io.github.numq.blueprint.runtime.component.material

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ColorRole {
    @SerialName("COLOR_PRIMARY")
    PRIMARY,

    @SerialName("COLOR_ON_PRIMARY")
    ON_PRIMARY,

    @SerialName("COLOR_SECONDARY")
    SECONDARY,

    @SerialName("COLOR_ON_SECONDARY")
    ON_SECONDARY,

    @SerialName("COLOR_ERROR")
    ERROR,

    @SerialName("COLOR_ON_ERROR")
    ON_ERROR,

    @SerialName("COLOR_SURFACE")
    SURFACE,

    @SerialName("COLOR_ON_SURFACE")
    ON_SURFACE,

    @SerialName("COLOR_SURFACE_VARIANT")
    SURFACE_VARIANT,

    @SerialName("COLOR_ON_SURFACE_VARIANT")
    ON_SURFACE_VARIANT,

    @SerialName("COLOR_BACKGROUND")
    BACKGROUND,

    @SerialName("COLOR_ON_BACKGROUND")
    ON_BACKGROUND,

    @SerialName("COLOR_OUTLINE")
    OUTLINE
}