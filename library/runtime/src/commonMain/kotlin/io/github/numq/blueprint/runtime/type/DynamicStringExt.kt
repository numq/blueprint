package io.github.numq.blueprint.runtime.type

import io.github.numq.blueprint.runtime.fp.Either
import io.github.numq.blueprint.runtime.fp.left
import io.github.numq.blueprint.runtime.fp.right

private fun DynamicString.resolveSafe(state: Map<String, String>): Either<ResolveError, String> = when (this) {
    is DynamicString.Literal -> value.right()

    is DynamicString.StateKey -> state[key]?.right() ?: ResolveError.MissingKey(key).left()
}

fun DynamicString.resolve(state: Map<String, String>) = resolveSafe(state).getOrElse { "" }