package io.github.numq.blueprint.example.client

import io.github.numq.blueprint.runtime.blueprintSerializersModule
import io.github.numq.blueprint.runtime.component.ComponentPayload
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic

val applicationSerializersModule = blueprintSerializersModule + SerializersModule {
    polymorphic(ComponentPayload::class) {
        // todo
    }
}