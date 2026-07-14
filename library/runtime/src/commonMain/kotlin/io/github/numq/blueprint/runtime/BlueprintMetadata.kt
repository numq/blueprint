package io.github.numq.blueprint.runtime

import kotlinx.serialization.Serializable

@Serializable
data class BlueprintMetadata(
    val title: String? = null,
    val description: String? = null,
    val createdAtMillis: Long = 0L,
    val updatedAtMillis: Long = 0L,
    val customFields: Map<String, String> = emptyMap()
)