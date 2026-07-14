package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalIntentHandler
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.component.MaterialPayload
import io.github.numq.blueprint.runtime.component.material.CardVariant

@OptIn(ExperimentalMaterial3Api::class)
object CardRenderer : ComponentRenderer<MaterialPayload.Card> {
    private const val INTENT_TYPE = "CARD_CLICK"

    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.Card, renderer: BlueprintRenderer) {
        val intentHandler = LocalIntentHandler.current

        val modifier = node.modifiers.toComposeModifier()

        val onClick = {
            payload.onClickIntentId?.let { id ->
                intentHandler.onIntent(Intent(id = id, type = INTENT_TYPE, nodeKey = node.key))
            } ?: Unit
        }

        val content: @Composable ColumnScope.() -> Unit = {
            node.children.forEach { child ->
                renderer.render(child)
            }
        }

        if (payload.onClickIntentId != null) {
            when (payload.variant) {
                CardVariant.ELEVATED -> ElevatedCard(onClick = onClick, modifier = modifier, content = content)

                CardVariant.FILLED -> Card(onClick = onClick, modifier = modifier, content = content)

                CardVariant.OUTLINED -> OutlinedCard(onClick = onClick, modifier = modifier, content = content)
            }
        } else {
            when (payload.variant) {
                CardVariant.ELEVATED -> ElevatedCard(modifier = modifier, content = content)

                CardVariant.FILLED -> Card(modifier = modifier, content = content)

                CardVariant.OUTLINED -> OutlinedCard(modifier = modifier, content = content)
            }
        }
    }
}