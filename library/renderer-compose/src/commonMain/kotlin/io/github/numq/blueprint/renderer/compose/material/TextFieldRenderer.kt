package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalBlueprintState
import io.github.numq.blueprint.renderer.local.LocalIntentHandler
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.IntentPayload
import io.github.numq.blueprint.runtime.component.MaterialPayload
import io.github.numq.blueprint.runtime.type.resolve

object TextFieldRenderer : ComponentRenderer<MaterialPayload.TextField> {
    private const val INTENT_TYPE = "TEXT_CHANGE"

    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.TextField, renderer: BlueprintRenderer) {
        val state = LocalBlueprintState.current

        val intentHandler = LocalIntentHandler.current

        val serverValue = payload.value.resolve(state)

        var localValue by remember(serverValue) { mutableStateOf(serverValue) }

        OutlinedTextField(value = localValue, onValueChange = { newValue ->
            localValue = newValue

            payload.onChangeIntentId?.let { intentId ->
                intentHandler.onIntent(
                    Intent(
                        id = intentId,
                        type = INTENT_TYPE,
                        nodeKey = node.key,
                        payload = IntentPayload.TextValue(newValue)
                    )
                )
            }
        }, enabled = payload.enabled.resolve(state), modifier = node.modifiers.toComposeModifier(), placeholder = {
            payload.placeholder?.let { placeholder ->
                Text(placeholder.resolve(state))
            }
        })
    }
}