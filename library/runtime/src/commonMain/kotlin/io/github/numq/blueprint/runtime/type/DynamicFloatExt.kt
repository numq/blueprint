package io.github.numq.blueprint.runtime.type

fun DynamicFloat.resolve(state: Map<String, String>) = when (this) {
    is DynamicFloat.Literal -> this.value

    is DynamicFloat.StateKey -> state[this.key]?.toFloatOrNull() ?: 0f
}