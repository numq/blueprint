package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.BlueprintMetadata
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.LayoutPayload

/**
 * Top-level builder for constructing complete [Blueprint] screen instances.
 *
 * @param id unique screen or layout identifier.
 */
class BlueprintBuilder(private val id: String) {
    private var metadata: BlueprintMetadata? = null

    private val state = mutableMapOf<String, String>()

    private lateinit var rootNode: BlueprintNode

    /**
     * Configures optional screen metadata (title, description, custom fields).
     */
    fun metadata(title: String? = null, description: String? = null, build: MetadataBuilder.() -> Unit = {}) {
        val builder = MetadataBuilder().apply {
            this.title = title
            this.description = description
        }.apply(build)

        metadata = builder.build()
    }

    /**
     * Populates initial state key-value pairs associated with this screen.
     */
    fun state(vararg pairs: Pair<String, String>) {
        state.putAll(pairs)
    }

    /**
     * Configures the root UI component tree.
     */
    fun root(builder: BlueprintDsl.() -> Unit) {
        val dsl = BlueprintDsl().apply(builder)

        rootNode = dsl.nodes.firstOrNull() ?: BlueprintNode(key = "empty_root", payload = LayoutPayload.Box())
    }

    internal fun build(): Blueprint = Blueprint(
        id = id, metadata = metadata, state = state, root = rootNode
    )
}

/**
 * Entry point function for creating a [Blueprint] screen model declaratively.
 *
 * @param id screen identifier.
 * @param block layout builder configuration block.
 * @return fully constructed [Blueprint] instance ready for serialization.
 */
fun blueprint(
    id: String = "screen", block: BlueprintBuilder.() -> Unit
): Blueprint = BlueprintBuilder(id = id).apply(block).build()