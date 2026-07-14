package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalBlueprintState
import io.github.numq.blueprint.renderer.local.LocalIntentHandler
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.component.MaterialPayload
import io.github.numq.blueprint.runtime.component.material.ButtonVariant
import io.github.numq.blueprint.runtime.type.resolve

object ButtonRenderer : ComponentRenderer<MaterialPayload.Button> {
    private const val INTENT_TYPE = "CLICK"

    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.Button, renderer: BlueprintRenderer) {
        val state = LocalBlueprintState.current

        val intentHandler = LocalIntentHandler.current

        val onClick = {
            payload.onClickIntentId?.let { id ->
                intentHandler.onIntent(Intent(id = id, type = INTENT_TYPE, nodeKey = node.key))
            } ?: Unit
        }

        val enabled = payload.enabled.resolve(state)

        val modifier = node.modifiers.toComposeModifier()

        val content: @Composable RowScope.() -> Unit = {
            Text(text = payload.text.resolve(state))
        }

        when (payload.variant) {
            ButtonVariant.FILLED -> Button(onClick = onClick, enabled = enabled, modifier = modifier, content = content)

            ButtonVariant.OUTLINED -> OutlinedButton(
                onClick = onClick, enabled = enabled, modifier = modifier, content = content
            )

            ButtonVariant.TEXT -> TextButton(
                onClick = onClick, enabled = enabled, modifier = modifier, content = content
            )

            ButtonVariant.ELEVATED -> ElevatedButton(
                onClick = onClick, enabled = enabled, modifier = modifier, content = content
            )

            ButtonVariant.FILLED_TONAL -> FilledTonalButton(
                onClick = onClick, enabled = enabled, modifier = modifier, content = content
            )
        }
    }
}