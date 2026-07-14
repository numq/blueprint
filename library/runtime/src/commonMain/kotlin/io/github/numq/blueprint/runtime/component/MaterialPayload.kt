package io.github.numq.blueprint.runtime.component

import io.github.numq.blueprint.runtime.component.material.*
import io.github.numq.blueprint.runtime.type.DynamicBool
import io.github.numq.blueprint.runtime.type.DynamicFloat
import io.github.numq.blueprint.runtime.type.DynamicString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface MaterialPayload : ComponentPayload {
    @Serializable
    @SerialName("material.Text")
    data class Text(
        val content: DynamicString,
        val size: TextSize = TextSize.BODY_MEDIUM,
        val color: ColorRole = ColorRole.ON_SURFACE,
        val colorHex: String? = null,
        val align: TextAlign = TextAlign.START,
        val maxLines: Int = Int.MAX_VALUE
    ) : MaterialPayload

    @Serializable
    @SerialName("material.Button")
    data class Button(
        val text: DynamicString,
        val variant: ButtonVariant = ButtonVariant.FILLED,
        val enabled: DynamicBool = DynamicBool.Literal(true),
        val onClickIntentId: String? = null
    ) : MaterialPayload

    @Serializable
    @SerialName("material.Card")
    data class Card(val variant: CardVariant = CardVariant.ELEVATED, val onClickIntentId: String? = null) :
        MaterialPayload

    @Serializable
    @SerialName("material.Icon")
    data class Icon(
        val name: String, val tint: ColorRole = ColorRole.ON_SURFACE, val size: IconSize = IconSize.MEDIUM
    ) : MaterialPayload

    @Serializable
    @SerialName("material.TextField")
    data class TextField(
        val value: DynamicString,
        val placeholder: DynamicString? = null,
        val enabled: DynamicBool = DynamicBool.Literal(true),
        val onChangeIntentId: String? = null
    ) : MaterialPayload

    @Serializable
    @SerialName("material.Checkbox")
    data class Checkbox(
        val checked: DynamicBool = DynamicBool.Literal(false),
        val enabled: DynamicBool = DynamicBool.Literal(true),
        val onChangeIntentId: String? = null
    ) : MaterialPayload

    @Serializable
    @SerialName("material.Switch")
    data class Switch(
        val checked: DynamicBool = DynamicBool.Literal(false),
        val enabled: DynamicBool = DynamicBool.Literal(true),
        val onChangeIntentId: String? = null
    ) : MaterialPayload

    @Serializable
    @SerialName("material.ProgressIndicator")
    data class ProgressIndicator(val isLinear: Boolean = false, val progress: DynamicFloat? = null) : MaterialPayload

    @Serializable
    @SerialName("material.Image")
    data class Image(val url: DynamicString, val contentDescription: String? = null) : MaterialPayload
}