package io.github.numq.blueprint.runtime.type

fun DynamicBool.resolve(state: Map<String, String>) = when (this) {
    is DynamicBool.Literal -> this.value

    is DynamicBool.StateKey -> state[this.key]?.toBooleanStrictOrNull() ?: false
}