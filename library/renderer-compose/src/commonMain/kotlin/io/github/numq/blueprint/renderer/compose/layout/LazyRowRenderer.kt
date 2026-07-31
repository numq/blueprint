package io.github.numq.blueprint.renderer.compose.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalIntentHandler
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.action.Intent
import io.github.numq.blueprint.runtime.action.IntentPayload
import io.github.numq.blueprint.runtime.component.LayoutPayload
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

object LazyRowRenderer : ComponentRenderer<LayoutPayload.LazyRow> {
    @Composable
    override fun render(node: BlueprintNode, payload: LayoutPayload.LazyRow, renderer: BlueprintRenderer) {
        val intentHandler = LocalIntentHandler.current

        val listState = rememberLazyListState()

        payload.onLoadMoreIntentId?.let { intent ->
            LaunchedEffect(listState, intent, payload.loadMoreThreshold) {
                snapshotFlow {
                    val layoutInfo = listState.layoutInfo

                    val totalItems = layoutInfo.totalItemsCount

                    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                    totalItems to lastVisibleItem
                }.filter { (total, lastVisible) ->
                    total > 0 && lastVisible >= (total - payload.loadMoreThreshold)
                }.distinctUntilChanged { old, new -> old.first == new.first }.collect { (total, _) ->
                    intentHandler.onIntent(
                        Intent(
                            id = intent, type = "LOAD_MORE", nodeKey = node.key, payload = IntentPayload.IntValue(total)
                        )
                    )
                }
            }
        }

        LazyRow(
            state = listState,
            modifier = node.modifiers.toComposeModifier(),
            contentPadding = PaddingValues(payload.contentPadding.dp),
            reverseLayout = payload.reversed,
            horizontalArrangement = payload.horizontalArrangement.toHorizontal(),
            verticalAlignment = payload.verticalAlignment.toVertical()
        ) {
            items(
                items = node.children,
                key = { child -> child.key },
                contentType = { child -> child.payload::class.simpleName }) { child ->
                renderer.render(child)
            }
        }
    }
}