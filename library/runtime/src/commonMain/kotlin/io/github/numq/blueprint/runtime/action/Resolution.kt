package io.github.numq.blueprint.runtime.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resolution(
    @SerialName("delta_blocks") val deltaBlocks: List<StateDeltaBlock> = emptyList(),
    @SerialName("effects") val effects: List<Effect> = emptyList(),
    @SerialName("target_node") val targetNode: String? = null
)