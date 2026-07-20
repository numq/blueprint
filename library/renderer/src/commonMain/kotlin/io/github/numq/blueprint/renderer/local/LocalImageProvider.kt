package io.github.numq.blueprint.renderer.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

typealias ImageProvider = @Composable (url: String, contentDescription: String?, modifier: Modifier) -> Unit

val LocalImageProvider = compositionLocalOf<ImageProvider> {
    error("ImageProvider not provided. You must provide an implementation via CompositionLocalProvider.")
}