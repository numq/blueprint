package io.github.numq.blueprint.dsl.type

import io.github.numq.blueprint.runtime.type.DynamicString

internal val String.asDynamic get() = DynamicString.Literal(this)

fun bindString(key: String) = DynamicString.StateKey(key)