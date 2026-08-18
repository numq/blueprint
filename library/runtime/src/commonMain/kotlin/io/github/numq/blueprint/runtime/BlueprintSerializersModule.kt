package io.github.numq.blueprint.runtime

import io.github.numq.blueprint.runtime.action.IntentPayload
import io.github.numq.blueprint.runtime.component.ComponentPayload
import io.github.numq.blueprint.runtime.component.LayoutPayload
import io.github.numq.blueprint.runtime.component.MaterialPayload
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val layoutPayloadSerializersModule = SerializersModule {
    polymorphic(ComponentPayload::class) {
        subclass(LayoutPayload.Box::class)
        subclass(LayoutPayload.Column::class)
        subclass(LayoutPayload.Row::class)
        subclass(LayoutPayload.Spacer::class)
        subclass(LayoutPayload.LazyColumn::class)
        subclass(LayoutPayload.LazyRow::class)
    }
}

private val materialPayloadSerializersModule = SerializersModule {
    polymorphic(ComponentPayload::class) {
        subclass(MaterialPayload.Text::class)
        subclass(MaterialPayload.Button::class)
        subclass(MaterialPayload.Card::class)
        subclass(MaterialPayload.Icon::class)
        subclass(MaterialPayload.TextField::class)
        subclass(MaterialPayload.Checkbox::class)
        subclass(MaterialPayload.Switch::class)
        subclass(MaterialPayload.ProgressIndicator::class)
        subclass(MaterialPayload.Image::class)
    }
}

private val intentPayloadSerializersModule = SerializersModule {
    polymorphic(IntentPayload::class) {
        subclass(IntentPayload.Empty::class)
        subclass(IntentPayload.TextValue::class)
        subclass(IntentPayload.BoolValue::class)
        subclass(IntentPayload.IntValue::class)
        subclass(IntentPayload.FloatValue::class)
    }
}

/**
 * Standard [SerializersModule] containing polymorphic mappings for all built-in
 * Blueprint layout payloads, Material 3 components, and action intent payloads.
 *
 * Must be registered in `kotlinx.serialization.protobuf.ProtoBuf` configurations
 * on both server and client engines.
 */
val blueprintSerializersModule: SerializersModule =
    layoutPayloadSerializersModule + materialPayloadSerializersModule + intentPayloadSerializersModule