package io.github.numq.blueprint.runtime.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface DynamicBool {
    @Serializable
    @SerialName("literal")
    data class Literal(val value: Boolean) : DynamicBool

    @Serializable
    @SerialName("state_key")
    data class StateKey(val key: String) : DynamicBool
}