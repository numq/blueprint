package io.github.numq.blueprint.runtime.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StateDeltaBlock(
    @SerialName("patches") val patches: Map<String, String>,
    @SerialName("previous_hash") val previousHash: String,
    @SerialName("new_hash") val newHash: String,
    @SerialName("signature") val signature: String = ""
)