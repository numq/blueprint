package io.github.numq.blueprint.renderer

import androidx.compose.runtime.Stable
import io.github.numq.blueprint.runtime.action.Intent

@Stable
fun interface IntentHandler {
    fun onIntent(intent: Intent)
}