package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.ComponentPayload

/**
 * DSL scope for constructing component node hierarchies on the server side.
 */
class BlueprintDsl {
    internal val nodes = mutableListOf<BlueprintNode>()

    private var counter = 0

    private fun generateKey(prefix: String) = "${prefix}_${counter++}"

    /**
     * Emits a new component node into the current layout scope.
     *
     * @param payload component payload data instance.
     * @param key optional explicit node key (auto-generated if omitted).
     * @param modifiers lambda configuring node layout/visual modifiers.
     * @param children nested children builder block.
     */
    fun node(
        payload: ComponentPayload,
        key: String? = null,
        modifiers: ModifierScope.() -> Unit = {},
        children: BlueprintDsl.() -> Unit = {}
    ) {
        val modifierList = ModifierScope().apply(modifiers).build()

        val childrenNodes = BlueprintDsl().apply(children).nodes

        nodes.add(
            BlueprintNode(
                key = key ?: generateKey(payload::class.simpleName?.lowercase() ?: "node"),
                payload = payload,
                modifiers = modifierList,
                children = childrenNodes,
                slots = emptyMap()
            )
        )
    }
}