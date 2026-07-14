package io.github.numq.blueprint.renderer.local

import androidx.compose.runtime.compositionLocalOf
import io.github.numq.blueprint.renderer.IntentHandler

val LocalIntentHandler = compositionLocalOf<IntentHandler> {
    error("Intent handler not provided")
}