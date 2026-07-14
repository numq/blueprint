package io.github.numq.blueprint.renderer.compose

import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.local.LocalErrorHandler
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.ComponentPayload
import kotlin.reflect.KClass

class BlueprintComposeRegistry : BlueprintRenderer {
    private val renderers = mutableMapOf<KClass<out ComponentPayload>, ComponentRenderer<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : ComponentPayload> register(kClass: KClass<T>, renderer: ComponentRenderer<T>) {
        renderers[kClass] = renderer as ComponentRenderer<*>
    }

    @Composable
    override fun render(node: BlueprintNode) {
        val payload = node.payload

        @Suppress("UNCHECKED_CAST") val renderer = renderers[payload::class] as? ComponentRenderer<ComponentPayload>

        when (renderer) {
            null -> {
                val error = IllegalStateException(
                    "No renderer registered for component type: ${payload::class.simpleName}"
                )

                LocalErrorHandler.current.onError(error, node)

                LocalErrorHandler.current.renderError(node, error)
            }

            else -> renderer.render(node, payload, this)
        }
    }
}