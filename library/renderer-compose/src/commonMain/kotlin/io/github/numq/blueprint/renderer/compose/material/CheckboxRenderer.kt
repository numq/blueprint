package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.material3.Checkbox
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

object CheckboxRenderer : ComponentRenderer<MaterialPayload.Checkbox> {
    private const val INTENT_TYPE = "CHECK_CHANGE"

    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.Checkbox, renderer: BlueprintRenderer) {
        val state = LocalBlueprintState.current

        val intentHandler = LocalIntentHandler.current

        val serverValue = payload.checked.resolve(state)

        var localValue by remember(serverValue) { mutableStateOf(serverValue) }

        Checkbox(
            checked = localValue, onCheckedChange = { newValue ->
                localValue = newValue
                payload.onChangeIntentId?.let { intentId ->
                    intentHandler.onIntent(
                        Intent(
                            id = intentId,
                            type = INTENT_TYPE,
                            nodeKey = node.key,
                            payload = IntentPayload.BoolValue(newValue)
                        )
                    )
                }
            }, enabled = payload.enabled.resolve(state), modifier = node.modifiers.toComposeModifier()
        )
    }
}