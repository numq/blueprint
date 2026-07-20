package io.github.numq.blueprint.chain

sealed interface ChainError {
    data class HashMismatch(val expected: String, val actual: String?) : ChainError

    data class SignatureVerificationFailed(val blockHash: String) : ChainError

    data object CannotPopEmptyChain : ChainError
}