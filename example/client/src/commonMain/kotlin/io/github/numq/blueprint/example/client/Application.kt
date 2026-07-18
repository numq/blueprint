package io.github.numq.blueprint.example.client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.numq.blueprint.renderer.IntentHandler
import io.github.numq.blueprint.renderer.compose.createDefaultBlueprintRegistry
import io.github.numq.blueprint.runtime.action.Intent
import io.ktor.client.*
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun Application(client: HttpClient) {
    val scope = rememberCoroutineScope()

    val store = remember { ApplicationStore(scope, client) }

    val state by store.state.collectAsState()

    val intentHandler = remember { IntentHandler(store::dispatch) }

    val renderer = remember { createDefaultBlueprintRegistry() }

    LaunchedEffect(Unit) {
        store.dispatch(Intent(id = "start", type = "SYSTEM", nodeKey = "root"))
    }

    var showOverlay by remember { mutableStateOf(false) }

    LaunchedEffect(state.isLoading) {
        if (state.isLoading) {
            delay(150.milliseconds)

            showOverlay = true
        } else {
            showOverlay = false
        }
    }


    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            when {
                state.chain.current == null && state.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                state.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                }

                else -> Box(Modifier.fillMaxSize()) {
                    state.chain.current?.let { blueprint ->
                        renderer.render(blueprint = blueprint, intentHandler = intentHandler)
                    }

                    if (showOverlay && state.chain.current != null) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}