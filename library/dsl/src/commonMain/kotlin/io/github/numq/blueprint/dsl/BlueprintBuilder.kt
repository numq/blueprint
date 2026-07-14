package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.BlueprintMetadata
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.LayoutPayload

class BlueprintBuilder(private val id: String) {
    private var metadata: BlueprintMetadata? = null

    private val state = mutableMapOf<String, String>()

    private lateinit var rootNode: BlueprintNode

    fun metadata(title: String? = null, description: String? = null, build: MetadataBuilder.() -> Unit = {}) {
        val builder = MetadataBuilder().apply {
            this.title = title
            this.description = description
        }.apply(build)

        metadata = builder.build()
    }

    fun state(vararg pairs: Pair<String, String>) {
        state.putAll(pairs)
    }

    fun root(builder: BlueprintDsl.() -> Unit) {
        val dsl = BlueprintDsl().apply(builder)

        rootNode = dsl.nodes.firstOrNull() ?: BlueprintNode(key = "empty_root", payload = LayoutPayload.Box())
    }

    internal fun build(): Blueprint = Blueprint(
        id = id, metadata = metadata, state = state, root = rootNode
    )
}

fun blueprint(id: String = "screen", block: BlueprintBuilder.() -> Unit) = BlueprintBuilder(id).apply(block).build()