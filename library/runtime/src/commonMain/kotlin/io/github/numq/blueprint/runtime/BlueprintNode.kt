package io.github.numq.blueprint.runtime

import io.github.numq.blueprint.runtime.component.ComponentPayload
import io.github.numq.blueprint.runtime.modifier.NodeModifier
import kotlinx.serialization.Serializable

/**
 * Represents an individual component node within a Blueprint UI tree.
 *
 * @property key unique identifier for this node within the screen layout.
 * @property payload data payload defining component properties (e.g. Text, Button, Column).
 * @property modifiers list of layout/styling modifiers applied to this node.
 * @property children child nodes nested within this component.
 * @property slots named slots containing child sub-trees.
 */
@Serializable
data class BlueprintNode(
    val key: String,
    val payload: ComponentPayload,
    val modifiers: List<NodeModifier> = emptyList(),
    val children: List<BlueprintNode> = emptyList(),
    val slots: Map<String, BlueprintNode> = emptyMap()
) {
    /**
     * Recursively searches for a node matching [searchKey] within this sub-tree.
     *
     * @param searchKey key to search for.
     * @return the matching [BlueprintNode], or `null` if not found.
     */
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

    /**
     * Collects all nodes in this hierarchy into a flattened list.
     *
     * @return list of all descendant nodes including `this`.
     */
    fun getAllNodes(): List<BlueprintNode> = buildList {
        add(this@BlueprintNode)

        children.forEach { child -> addAll(child.getAllNodes()) }

        slots.values.forEach { value -> addAll(value.getAllNodes()) }
    }

    /**
     * Filters all nodes in the sub-tree by a specific payload type [T].
     *
     * @param T target component payload type.
     * @return list of matching nodes.
     */
    inline fun <reified T : ComponentPayload> findNodesByPayloadType(): List<BlueprintNode> =
        getAllNodes().filter { node ->
            node.payload is T
        }
}