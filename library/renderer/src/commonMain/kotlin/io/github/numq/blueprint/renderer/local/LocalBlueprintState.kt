package io.github.numq.blueprint.renderer.local

import androidx.compose.runtime.compositionLocalOf

val LocalBlueprintState = compositionLocalOf<Map<String, String>> {
    error("Blueprint state not provided")
}