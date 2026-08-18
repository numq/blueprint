package io.github.numq.blueprint.runtime.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a dynamically resolved string value in Blueprint UI components.
 * Can either be a static literal or a dynamic key resolved against the current screen state.
 */
@Serializable
sealed interface DynamicString {
    /**
     * A static string literal value.
     *
     * @property value the raw string literal.
     */
    @Serializable
    @SerialName("literal")
    data class Literal(val value: String) : DynamicString

    /**
     * A state key pointing to an entry in the Blueprint runtime state map.
     *
     * @property key state lookup key.
     */
    @Serializable
    @SerialName("state_key")
    data class StateKey(val key: String) : DynamicString
}