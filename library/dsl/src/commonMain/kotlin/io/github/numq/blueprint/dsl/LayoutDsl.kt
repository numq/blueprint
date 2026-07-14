package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.runtime.component.LayoutPayload
import io.github.numq.blueprint.runtime.component.layout.LayoutAlignment
import io.github.numq.blueprint.runtime.component.layout.LayoutArrangement

fun BlueprintDsl.Box(
    contentAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {},
    children: BlueprintDsl.() -> Unit
) {
    node(payload = LayoutPayload.Box(contentAlignment), key = key, modifiers = modifiers, children = children)
}

fun BlueprintDsl.Column(
    verticalArrangement: LayoutArrangement = LayoutArrangement.START,
    horizontalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
    spacing: Float = 0f,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {},
    children: BlueprintDsl.() -> Unit
) {
    node(
        payload = LayoutPayload.Column(verticalArrangement, horizontalAlignment, spacing),
        key = key,
        modifiers = modifiers,
        children = children
    )
}

fun BlueprintDsl.Row(
    horizontalArrangement: LayoutArrangement = LayoutArrangement.START,
    verticalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
    spacing: Float = 0f,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {},
    children: BlueprintDsl.() -> Unit
) {
    node(
        payload = LayoutPayload.Row(horizontalArrangement, verticalAlignment, spacing),
        key = key,
        modifiers = modifiers,
        children = children
    )
}

fun BlueprintDsl.LazyColumn(
    verticalArrangement: LayoutArrangement = LayoutArrangement.START,
    horizontalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
    contentPadding: Float = 0f,
    reversed: Boolean = false,
    onLoadMoreIntentId: String? = null,
    loadMoreThreshold: Int = 3,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {},
    items: BlueprintDsl.() -> Unit
) {
    node(
        payload = LayoutPayload.LazyColumn(
            verticalArrangement, horizontalAlignment, contentPadding, reversed, onLoadMoreIntentId, loadMoreThreshold
        ), key = key, modifiers = modifiers, children = items
    )
}

fun BlueprintDsl.LazyRow(
    horizontalArrangement: LayoutArrangement = LayoutArrangement.START,
    verticalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
    contentPadding: Float = 0f,
    reversed: Boolean = false,
    onLoadMoreIntentId: String? = null,
    loadMoreThreshold: Int = 3,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {},
    items: BlueprintDsl.() -> Unit
) {
    node(
        payload = LayoutPayload.LazyRow(
            horizontalArrangement, verticalAlignment, contentPadding, reversed, onLoadMoreIntentId, loadMoreThreshold
        ), key = key, modifiers = modifiers, children = items
    )
}

fun BlueprintDsl.Spacer(size: Float, key: String? = null, modifiers: ModifierScope.() -> Unit = {}) {
    node(payload = LayoutPayload.Spacer(size), key = key, modifiers = modifiers)
}