package io.github.numq.blueprint.renderer.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import io.github.numq.blueprint.runtime.modifier.NodeModifier

internal fun List<NodeModifier>.toComposeModifier() = fold(Modifier as Modifier) { acc, mod ->
    val shape = filterIsInstance<NodeModifier.CornerRadius>().firstOrNull()?.let {
        RoundedCornerShape(it.radius.dp)
    } ?: RectangleShape

    when (mod) {
        is NodeModifier.Padding -> acc.padding(
            start = mod.start.dp, top = mod.top.dp, end = mod.end.dp, bottom = mod.bottom.dp
        )

        is NodeModifier.Width -> mod.width.takeUnless(Float::isNaN)?.dp?.let { width ->
            acc.width(width = width)
        } ?: acc

        is NodeModifier.Height -> mod.height.takeUnless(Float::isNaN)?.dp?.let { height ->
            acc.height(height = height)
        } ?: acc

        is NodeModifier.Size -> {
            var sizeModifier: Modifier = acc

            mod.width.takeUnless(Float::isNaN)?.dp?.let { width ->
                sizeModifier = sizeModifier.width(width = width)
            }

            mod.height.takeUnless(Float::isNaN)?.dp?.let { height ->
                sizeModifier = sizeModifier.height(height = height)
            }

            sizeModifier
        }

        is NodeModifier.Background -> {
            val color = runCatching {
                val cleanHex = mod.colorHex.removePrefix("#")

                val colorLong = cleanHex.toLong(16)

                when (cleanHex.length) {
                    6 -> Color(colorLong or 0xFF000000)

                    else -> Color(colorLong)
                }
            }.getOrDefault(Color.Transparent)

            acc.background(color, shape)
        }

        is NodeModifier.Elevation -> acc.shadow(elevation = mod.elevation.dp, shape = shape, clip = false)

        is NodeModifier.CornerRadius -> acc.clip(shape)

        is NodeModifier.Alpha -> acc.alpha(mod.alpha)

        is NodeModifier.Weight -> acc

        is NodeModifier.FillMaxWidth -> acc.fillMaxWidth()

        is NodeModifier.FillMaxHeight -> acc.fillMaxHeight()

        is NodeModifier.FillMaxSize -> acc.fillMaxSize()
    }
}

internal val List<NodeModifier>.extractWeight: Float
    get() = this.filterIsInstance<NodeModifier.Weight>().firstOrNull()?.weight ?: 0f