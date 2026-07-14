package io.github.numq.blueprint.renderer.compose.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import io.github.numq.blueprint.runtime.component.layout.LayoutAlignment
import io.github.numq.blueprint.runtime.component.layout.LayoutArrangement

internal fun LayoutArrangement.toHorizontal(): Arrangement.Horizontal = when (this) {
    LayoutArrangement.START -> Arrangement.Start

    LayoutArrangement.CENTER -> Arrangement.Center

    LayoutArrangement.END -> Arrangement.End

    LayoutArrangement.SPACE_BETWEEN -> Arrangement.SpaceBetween

    LayoutArrangement.SPACE_AROUND -> Arrangement.SpaceAround

    LayoutArrangement.SPACE_EVENLY -> Arrangement.SpaceEvenly
}

internal fun LayoutArrangement.toVertical(): Arrangement.Vertical = when (this) {
    LayoutArrangement.START -> Arrangement.Top

    LayoutArrangement.CENTER -> Arrangement.Center

    LayoutArrangement.END -> Arrangement.Bottom

    LayoutArrangement.SPACE_BETWEEN -> Arrangement.SpaceBetween

    LayoutArrangement.SPACE_AROUND -> Arrangement.SpaceAround

    LayoutArrangement.SPACE_EVENLY -> Arrangement.SpaceEvenly
}

internal fun LayoutAlignment.toHorizontal(): Alignment.Horizontal = when (this) {
    LayoutAlignment.ALIGN_START -> Alignment.Start

    LayoutAlignment.ALIGN_CENTER -> Alignment.CenterHorizontally

    LayoutAlignment.ALIGN_END -> Alignment.End

    LayoutAlignment.ALIGN_STRETCH -> Alignment.Start
}

internal fun LayoutAlignment.toVertical(): Alignment.Vertical = when (this) {
    LayoutAlignment.ALIGN_START -> Alignment.Top

    LayoutAlignment.ALIGN_CENTER -> Alignment.CenterVertically

    LayoutAlignment.ALIGN_END -> Alignment.Bottom

    LayoutAlignment.ALIGN_STRETCH -> Alignment.Top
}

internal fun LayoutAlignment.toBoxAlignment(): Alignment = when (this) {
    LayoutAlignment.ALIGN_START -> Alignment.TopStart

    LayoutAlignment.ALIGN_CENTER -> Alignment.Center

    LayoutAlignment.ALIGN_END -> Alignment.BottomEnd

    LayoutAlignment.ALIGN_STRETCH -> Alignment.TopStart
}