package io.github.numq.blueprint.runtime.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface DynamicString {
    @Serializable
    @SerialName("literal")
    data class Literal(val value: String) : DynamicString

    @Serializable
    @SerialName("state_key")
    data class StateKey(val key: String) : DynamicString
}