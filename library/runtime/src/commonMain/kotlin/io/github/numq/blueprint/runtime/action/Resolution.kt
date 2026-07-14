package io.github.numq.blueprint.runtime.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resolution(
    @SerialName("state_patches") val statePatches: Map<String, String> = emptyMap(),

    @SerialName("effects") val effects: List<Effect> = emptyList(),

    @SerialName("target_node") val targetNode: String? = null
)