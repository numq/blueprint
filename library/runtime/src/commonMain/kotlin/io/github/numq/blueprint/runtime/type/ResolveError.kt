package io.github.numq.blueprint.runtime.type

sealed interface ResolveError {
    data class MissingKey(val key: String) : ResolveError

    data class ParseFailed(val key: String, val value: String, val targetType: String) : ResolveError
}