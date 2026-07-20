package io.github.numq.blueprint.renderer.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.numq.blueprint.runtime.component.material.IconSize

typealias IconProvider = @Composable (name: String, tint: Color, size: IconSize, modifier: Modifier) -> Unit

val LocalIconProvider = compositionLocalOf<IconProvider> {
    error("IconProvider not provided. You must provide an implementation via CompositionLocalProvider.")
}