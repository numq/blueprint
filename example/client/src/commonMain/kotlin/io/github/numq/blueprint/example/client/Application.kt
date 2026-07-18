package io.github.numq.blueprint.example.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.numq.blueprint.renderer.compose.createDefaultBlueprintRegistry
import io.github.numq.blueprint.runtime.action.Intent
import io.ktor.client.*

@Composable
fun Application(client: HttpClient) {
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
                state.error != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Error: ${state.error}", color = MaterialTheme.colorScheme.error)

                        IconButton(onClick = {
                            store.dispatch(Intent(id = "start", type = "SYSTEM", nodeKey = "root"))
                        }) {
                            Icon(Icons.Default.Refresh, null)
                        }
                    }
                }

                else -> when (val blueprint = state.currentBlueprint) {
                    null -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> Box(modifier = Modifier.fillMaxSize()) {
                        renderer.render(blueprint = blueprint, intentHandler = {
                            store.dispatch(it)
                        })

                        if (state.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = .1f)),
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
}