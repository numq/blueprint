package io.github.numq.blueprint.runtime.type

import io.github.numq.blueprint.runtime.fp.Either
import io.github.numq.blueprint.runtime.fp.left
import io.github.numq.blueprint.runtime.fp.right

private fun DynamicString.resolveSafe(state: Map<String, String>): Either<ResolveError, String> = when (this) {
    is DynamicString.Literal -> value.right()
    is DynamicString.StateKey -> state[key]?.right() ?: ResolveError.MissingKey(key).left()
}

/**
 * Resolves this [DynamicString] against the given [state] map.
 * Returns the resolved string, or empty string `""` if the state key is missing.
 *
 * @param state dynamic screen state map.
 * @return resolved string value.
 */
fun DynamicString.resolve(state: Map<String, String>): String = resolveSafe(state).getOrElse { "" }