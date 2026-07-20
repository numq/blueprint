package io.github.numq.blueprint.runtime.type

import io.github.numq.blueprint.runtime.fp.Either
import io.github.numq.blueprint.runtime.fp.left
import io.github.numq.blueprint.runtime.fp.right

private fun DynamicBool.resolveSafe(state: Map<String, String>): Either<ResolveError, Boolean> = when (this) {
    is DynamicBool.Literal -> value.right()

    is DynamicBool.StateKey -> {
        val value = state[key] ?: return ResolveError.MissingKey(key).left()

        value.toBooleanStrictOrNull()?.right() ?: ResolveError.ParseFailed(key, value, "Boolean").left()
    }
}

fun DynamicBool.resolve(state: Map<String, String>) = resolveSafe(state).getOrElse { false }