package io.github.numq.blueprint.renderer.compose.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalIntentHandler
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.IntentPayload
import io.github.numq.blueprint.runtime.component.LayoutPayload

object LazyColumnRenderer : ComponentRenderer<LayoutPayload.LazyColumn> {
    @Composable
    override fun render(node: BlueprintNode, payload: LayoutPayload.LazyColumn, renderer: BlueprintRenderer) {
        val intentHandler = LocalIntentHandler.current

        val listState = rememberLazyListState()

        payload.onLoadMoreIntentId?.let { id ->
            val shouldLoadMore by remember {
                derivedStateOf {
                    val totalItems = listState.layoutInfo.totalItemsCount

                    val lastVisible = (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

                    totalItems > 0 && lastVisible >= (totalItems - payload.loadMoreThreshold)
                }
            }

            LaunchedEffect(shouldLoadMore) {
                if (shouldLoadMore) {
                    intentHandler.onIntent(
                        Intent(
                            id = id,
                            type = "LOAD_MORE",
                            nodeKey = node.key,
                            payload = IntentPayload.IntValue(node.children.size)
                        )
                    )
                }
            }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(payload.contentPadding.dp),
            modifier = node.modifiers.toComposeModifier(),
            verticalArrangement = payload.verticalArrangement.toVertical(),
            horizontalAlignment = payload.horizontalAlignment.toHorizontal()
        ) {
            itemsIndexed(node.children, key = { _, child -> child.key }) { _, child ->
                renderer.render(child)
            }
        }
    }
}