package io.github.numq.blueprint.runtime.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class IntentPriority {
    @SerialName("NORMAL")
    NORMAL,

    @SerialName("HIGH")
    HIGH,

    @SerialName("CRITICAL")
    CRITICAL
}