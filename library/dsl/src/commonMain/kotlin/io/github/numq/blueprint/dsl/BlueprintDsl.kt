package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.ComponentPayload

class BlueprintDsl {
    internal val nodes = mutableListOf<BlueprintNode>()

    private var counter = 0

    private fun generateKey(prefix: String) = "${prefix}_${counter++}"

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