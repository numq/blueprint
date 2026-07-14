package io.github.numq.blueprint.runtime

import kotlinx.serialization.Serializable

@Serializable
data class Blueprint(
    val id: String,
    val version: String = "1.0",
    val metadata: BlueprintMetadata? = null,
    val state: Map<String, String> = emptyMap(),
    val root: BlueprintNode
)