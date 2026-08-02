package io.github.numq.blueprint.runtime

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

val blueprintSerializersModule = layoutPayloadSerializersModule + materialPayloadSerializersModule