package io.github.numq.blueprint.renderer.local

import androidx.compose.runtime.compositionLocalOf
import io.github.numq.blueprint.renderer.DefaultErrorHandler
import io.github.numq.blueprint.renderer.ErrorHandler

val LocalErrorHandler = compositionLocalOf<ErrorHandler> {
    DefaultErrorHandler()
}