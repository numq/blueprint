package io.github.numq.blueprint.runtime.component.material

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TextSize {
    @SerialName("TEXT_DISPLAY_LARGE")
    DISPLAY_LARGE,

    @SerialName("TEXT_DISPLAY_MEDIUM")
    DISPLAY_MEDIUM,

    @SerialName("TEXT_DISPLAY_SMALL")
    DISPLAY_SMALL,

    @SerialName("TEXT_HEADLINE_LARGE")
    HEADLINE_LARGE,

    @SerialName("TEXT_HEADLINE_MEDIUM")
    HEADLINE_MEDIUM,

    @SerialName("TEXT_HEADLINE_SMALL")
    HEADLINE_SMALL,

    @SerialName("TEXT_TITLE_LARGE")
    TITLE_LARGE,

    @SerialName("TEXT_TITLE_MEDIUM")
    TITLE_MEDIUM,

    @SerialName("TEXT_TITLE_SMALL")
    TITLE_SMALL,

    @SerialName("TEXT_BODY_LARGE")
    BODY_LARGE,

    @SerialName("TEXT_BODY_MEDIUM")
    BODY_MEDIUM,

    @SerialName("TEXT_BODY_SMALL")
    BODY_SMALL,

    @SerialName("TEXT_LABEL_LARGE")
    LABEL_LARGE,

    @SerialName("TEXT_LABEL_MEDIUM")
    LABEL_MEDIUM,

    @SerialName("TEXT_LABEL_SMALL")
    LABEL_SMALL
}