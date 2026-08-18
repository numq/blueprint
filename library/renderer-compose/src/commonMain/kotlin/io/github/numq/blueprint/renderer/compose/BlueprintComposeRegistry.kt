package io.github.numq.blueprint.renderer.compose

import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.local.LocalErrorHandler
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.ComponentPayload
import kotlin.reflect.KClass

/**
 * Registry mapping payload types to Compose component renderers.
 * Serves as the primary entry point for drawing [BlueprintNode] UI trees in Jetpack Compose Multiplatform.
 */
class BlueprintComposeRegistry : BlueprintRenderer {
    private val renderers = mutableMapOf<KClass<out ComponentPayload>, ComponentRenderer<*>>()

    /**
     * Registers a custom or standard [ComponentRenderer] for a specific payload class [kClass].
     *
     * @param kClass class reference of the payload type.
     * @param renderer renderer instance responsible for drawing this component type.
     */
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