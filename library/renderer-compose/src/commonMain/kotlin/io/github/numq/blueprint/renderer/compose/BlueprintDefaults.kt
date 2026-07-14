package io.github.numq.blueprint.renderer.compose

import io.github.numq.blueprint.renderer.compose.layout.*
import io.github.numq.blueprint.renderer.compose.material.*
import io.github.numq.blueprint.runtime.component.LayoutPayload
import io.github.numq.blueprint.runtime.component.MaterialPayload

fun createDefaultBlueprintRegistry(): BlueprintComposeRegistry {
    val registry = BlueprintComposeRegistry()

    registry.register(LayoutPayload.Box::class, BoxRenderer)
    registry.register(LayoutPayload.Column::class, ColumnRenderer)
    registry.register(LayoutPayload.LazyColumn::class, LazyColumnRenderer)
    registry.register(LayoutPayload.LazyRow::class, LazyRowRenderer)
    registry.register(LayoutPayload.Row::class, RowRenderer)
    registry.register(LayoutPayload.Spacer::class, SpacerRenderer)

    registry.register(MaterialPayload.Button::class, ButtonRenderer)
    registry.register(MaterialPayload.Card::class, CardRenderer)
    registry.register(MaterialPayload.Checkbox::class, CheckboxRenderer)
    registry.register(MaterialPayload.Icon::class, IconRenderer)
    registry.register(MaterialPayload.ProgressIndicator::class, ProgressIndicatorRenderer)
    registry.register(MaterialPayload.Switch::class, SwitchRenderer)
    registry.register(MaterialPayload.TextField::class, TextFieldRenderer)
    registry.register(MaterialPayload.Text::class, TextRenderer)

    return registry
}