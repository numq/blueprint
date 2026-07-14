package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import io.github.numq.blueprint.runtime.component.material.ColorRole
import io.github.numq.blueprint.runtime.component.material.TextAlign
import io.github.numq.blueprint.runtime.component.material.TextSize
import androidx.compose.ui.text.style.TextAlign as ComposeTextAlign

@Composable
internal fun ColorRole.toComposeColor(): Color = when (this) {
    ColorRole.PRIMARY -> MaterialTheme.colorScheme.primary

    ColorRole.ON_PRIMARY -> MaterialTheme.colorScheme.onPrimary

    ColorRole.SECONDARY -> MaterialTheme.colorScheme.secondary

    ColorRole.ON_SECONDARY -> MaterialTheme.colorScheme.onSecondary

    ColorRole.ERROR -> MaterialTheme.colorScheme.error

    ColorRole.ON_ERROR -> MaterialTheme.colorScheme.onError

    ColorRole.SURFACE -> MaterialTheme.colorScheme.surface

    ColorRole.ON_SURFACE -> MaterialTheme.colorScheme.onSurface

    ColorRole.SURFACE_VARIANT -> MaterialTheme.colorScheme.surfaceVariant

    ColorRole.ON_SURFACE_VARIANT -> MaterialTheme.colorScheme.onSurfaceVariant

    ColorRole.BACKGROUND -> MaterialTheme.colorScheme.background

    ColorRole.ON_BACKGROUND -> MaterialTheme.colorScheme.onBackground

    ColorRole.OUTLINE -> MaterialTheme.colorScheme.outline
}

@Composable
internal fun TextSize.toComposeTextStyle(): TextStyle = when (this) {
    TextSize.DISPLAY_LARGE -> MaterialTheme.typography.displayLarge

    TextSize.DISPLAY_MEDIUM -> MaterialTheme.typography.displayMedium

    TextSize.DISPLAY_SMALL -> MaterialTheme.typography.displaySmall

    TextSize.HEADLINE_LARGE -> MaterialTheme.typography.headlineLarge

    TextSize.HEADLINE_MEDIUM -> MaterialTheme.typography.headlineMedium

    TextSize.HEADLINE_SMALL -> MaterialTheme.typography.headlineSmall

    TextSize.TITLE_LARGE -> MaterialTheme.typography.titleLarge

    TextSize.TITLE_MEDIUM -> MaterialTheme.typography.titleMedium

    TextSize.TITLE_SMALL -> MaterialTheme.typography.titleSmall

    TextSize.BODY_LARGE -> MaterialTheme.typography.bodyLarge

    TextSize.BODY_MEDIUM -> MaterialTheme.typography.bodyMedium

    TextSize.BODY_SMALL -> MaterialTheme.typography.bodySmall

    TextSize.LABEL_LARGE -> MaterialTheme.typography.labelLarge

    TextSize.LABEL_MEDIUM -> MaterialTheme.typography.labelMedium

    TextSize.LABEL_SMALL -> MaterialTheme.typography.labelSmall
}

internal fun TextAlign.toComposeTextAlign(): ComposeTextAlign = when (this) {
    TextAlign.START -> ComposeTextAlign.Start

    TextAlign.CENTER -> ComposeTextAlign.Center

    TextAlign.END -> ComposeTextAlign.End
}