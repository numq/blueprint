package io.github.numq.blueprint.runtime

import io.github.numq.blueprint.runtime.component.ComponentPayload
import io.github.numq.blueprint.runtime.modifier.NodeModifier
import kotlinx.serialization.Serializable

@Serializable
data class BlueprintNode(
    val key: String,
    val payload: ComponentPayload,
    val modifiers: List<NodeModifier> = emptyList(),
    val children: List<BlueprintNode> = emptyList(),
    val slots: Map<String, BlueprintNode> = emptyMap()
) {
    fun findNodeByKey(searchKey: String): BlueprintNode? {
        if (this.key == searchKey) return this

        for (child in children) {
            child.findNodeByKey(searchKey)?.let { return it }
        }

        for (slot in slots.values) {
            slot.findNodeByKey(searchKey)?.let { return it }
        }

        return null
    }

    fun getAllNodes(): List<BlueprintNode> = buildList {
        add(this@BlueprintNode)

        children.forEach { addAll(it.getAllNodes()) }

        slots.values.forEach { addAll(it.getAllNodes()) }
    }

    inline fun <reified T : ComponentPayload> findNodesByPayloadType() = getAllNodes().filter { it.payload is T }
}