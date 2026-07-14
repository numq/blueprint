package io.github.numq.blueprint.runtime.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface IntentPayload {
    @Serializable
    @SerialName("text")
    data class TextValue(val value: String) : IntentPayload

    @Serializable
    @SerialName("bool")
    data class BoolValue(val value: Boolean) : IntentPayload

    @Serializable
    @SerialName("int")
    data class IntValue(val value: Int) : IntentPayload

    @Serializable
    @SerialName("float")
    data class FloatValue(val value: Float) : IntentPayload
}