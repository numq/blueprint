package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.runtime.BlueprintMetadata
import kotlin.time.Clock

class MetadataBuilder {
    var title: String? = null

    var description: String? = null

    val customFields = mutableMapOf<String, String>()

    fun customField(key: String, value: String) {
        customFields[key] = value
    }

    fun build() = BlueprintMetadata(
        title = title,
        description = description,
        createdAtMillis = Clock.System.now().toEpochMilliseconds(),
        customFields = customFields
    )
}