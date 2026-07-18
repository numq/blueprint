package io.github.numq.blueprint.chain

interface SignatureVerifier {
    fun verify(payload: String, signatureBase64: String): Boolean
}