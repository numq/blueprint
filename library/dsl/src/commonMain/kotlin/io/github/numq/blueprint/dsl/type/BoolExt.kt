package io.github.numq.blueprint.dsl.type

import io.github.numq.blueprint.runtime.type.DynamicBool

internal val Boolean.asDynamic get() = DynamicBool.Literal(this)

fun bindBool(key: String) = DynamicBool.StateKey(key)