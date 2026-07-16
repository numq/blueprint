package io.github.numq.blueprint.example.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.numq.blueprint.renderer.compose.createDefaultBlueprintRegistry
import io.github.numq.blueprint.runtime.action.Intent
import io.ktor.client.*

@Composable
fun Application(client: HttpClient) {
    var error by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val store = remember { ApplicationStore(scope = scope, client = client) }

    val state by store.state.collectAsState()

    val renderer = remember { createDefaultBlueprintRegistry() }

    LaunchedEffect(Unit) {
        store.dispatch(Intent(id = "start", type = "SYSTEM", nodeKey = "root"))
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
            when {
                state.currentBlueprint == null && isLoading -> Box(
                    Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
                }

                else -> Box(modifier = Modifier.fillMaxSize()) {
                    state.currentBlueprint?.let { blueprint ->
                        renderer.render(blueprint = blueprint, intentHandler = {
                            store.dispatch(it)
                        })
                    }

                    if (isLoading && state.currentBlueprint != null) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}