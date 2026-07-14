package io.github.numq.blueprint.dsl.type

import io.github.numq.blueprint.runtime.type.DynamicFloat

internal val Float.asDynamic get() = DynamicFloat.Literal(this)

fun bindFloat(key: String) = DynamicFloat.StateKey(key)