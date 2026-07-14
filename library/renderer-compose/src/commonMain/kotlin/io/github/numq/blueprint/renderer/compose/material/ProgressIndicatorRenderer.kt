package io.github.numq.blueprint.renderer.compose.material

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import io.github.numq.blueprint.renderer.BlueprintRenderer
import io.github.numq.blueprint.renderer.ComponentRenderer
import io.github.numq.blueprint.renderer.compose.toComposeModifier
import io.github.numq.blueprint.renderer.local.LocalBlueprintState
import io.github.numq.blueprint.runtime.BlueprintNode
import io.github.numq.blueprint.runtime.component.MaterialPayload
import io.github.numq.blueprint.runtime.type.resolve

object ProgressIndicatorRenderer : ComponentRenderer<MaterialPayload.ProgressIndicator> {
    @Composable
    override fun render(node: BlueprintNode, payload: MaterialPayload.ProgressIndicator, renderer: BlueprintRenderer) {
        val state = LocalBlueprintState.current

        val modifier = node.modifiers.toComposeModifier()

        val progress = payload.progress?.resolve(state)

        if (payload.isLinear) {
            if (progress != null) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = modifier,
                    color = ProgressIndicatorDefaults.linearColor,
                    trackColor = ProgressIndicatorDefaults.linearTrackColor,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
            } else {
                LinearProgressIndicator(modifier = modifier)
            }
        } else {
            if (progress != null) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = modifier,
                    color = ProgressIndicatorDefaults.circularColor,
                    strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                    trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                    strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                )
            } else {
                CircularProgressIndicator(modifier = modifier)
            }
        }
    }
}