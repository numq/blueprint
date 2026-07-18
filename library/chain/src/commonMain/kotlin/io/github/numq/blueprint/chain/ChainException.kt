package io.github.numq.blueprint.chain

class ChainException(override val message: String?) : Exception("Zero-Trust Violation: $message")