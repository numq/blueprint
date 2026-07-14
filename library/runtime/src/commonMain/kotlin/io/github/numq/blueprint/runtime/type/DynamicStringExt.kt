package io.github.numq.blueprint.runtime.type

fun DynamicString.resolve(state: Map<String, String>) = when (this) {
    is DynamicString.Literal -> this.value

    is DynamicString.StateKey -> state[this.key] ?: ""
}