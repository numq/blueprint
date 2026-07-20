package io.github.numq.blueprint.runtime.type

import io.github.numq.blueprint.runtime.fp.Either
import io.github.numq.blueprint.runtime.fp.left
import io.github.numq.blueprint.runtime.fp.right

private fun DynamicFloat.resolveSafe(state: Map<String, String>): Either<ResolveError, Float> = when (this) {
    is DynamicFloat.Literal -> value.right()

    is DynamicFloat.StateKey -> {
        val value = state[key] ?: return ResolveError.MissingKey(key).left()

        value.toFloatOrNull()?.right() ?: ResolveError.ParseFailed(key, value, "Float").left()
    }
}

fun DynamicFloat.resolve(state: Map<String, String>) = resolveSafe(state).getOrElse { 0f }