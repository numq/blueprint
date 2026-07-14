package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.runtime.modifier.NodeModifier

class ModifierScope {
    private val modifiers = mutableListOf<NodeModifier>()

    fun padding(start: Float = 0f, top: Float = 0f, end: Float = 0f, bottom: Float = 0f) {
        modifiers.add(NodeModifier.Padding(start, top, end, bottom))
    }

    fun padding(all: Float) {
        modifiers.add(NodeModifier.Padding(all, all, all, all))
    }

    fun width(width: Float = Float.NaN) {
        modifiers.add(NodeModifier.Width(width))
    }

    fun height(height: Float = Float.NaN) {
        modifiers.add(NodeModifier.Height(height))
    }

    fun size(width: Float = Float.NaN, height: Float = Float.NaN) {
        modifiers.add(NodeModifier.Size(width, height))
    }

    fun background(colorHex: String) {
        modifiers.add(NodeModifier.Background(colorHex))
    }

    fun elevation(elevation: Float) {
        modifiers.add(NodeModifier.Elevation(elevation))
    }

    fun cornerRadius(radius: Float) {
        modifiers.add(NodeModifier.CornerRadius(radius))
    }

    fun alpha(alpha: Float) {
        modifiers.add(NodeModifier.Alpha(alpha))
    }

    fun weight(weight: Float) {
        modifiers.add(NodeModifier.Weight(weight))
    }

    fun fillMaxWidth(fraction: Float = 1f) {
        modifiers.add(NodeModifier.FillMaxWidth(fraction))
    }

    fun fillMaxHeight(fraction: Float = 1f) {
        modifiers.add(NodeModifier.FillMaxHeight(fraction))
    }

    fun fillMaxSize(fraction: Float = 1f) {
        modifiers.add(NodeModifier.FillMaxSize(fraction))
    }

    internal fun build() = modifiers.toList()
}