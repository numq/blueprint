package io.github.numq.blueprint.example.server

import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.action.StateDeltaBlock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.Signature
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
object CryptoUtils {
    private val protoBuf = ProtoBuf

    // RSA (2048 bit)
    // private key: Vault/KMS, public key: client application
    private val keyPair = KeyPairGenerator.getInstance("RSA").apply {
        initialize(2048)
    }.generateKeyPair()

    val publicKeyBase64: String
        get() = Base64.getEncoder().encodeToString(keyPair.public.encoded)

    fun hash(payloadBytes: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")

        val bytes = digest.digest(payloadBytes)

        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun hashBlueprint(blueprint: Blueprint): String {
        val cleanBlueprint = blueprint.copy(hash = "", previousHash = "")

        val payloadBytes = protoBuf.encodeToByteArray(Blueprint.serializer(), cleanBlueprint)

        return hash(payloadBytes)
    }

    fun sign(payloadToSign: String): String {
        val signature = Signature.getInstance("SHA256withRSA")

        signature.initSign(keyPair.private)

        signature.update(payloadToSign.toByteArray(Charsets.UTF_8))

        return Base64.getEncoder().encodeToString(signature.sign())
    }

    fun createSignedBlueprint(blueprint: Blueprint, previousHash: String?): Blueprint {
        val bpWithPrev = blueprint.copy(previousHash = previousHash)

        val newHash = hashBlueprint(bpWithPrev)

        return bpWithPrev.copy(hash = newHash)
    }

    fun createDeltaBlock(currentBlueprintHash: String, patches: Map<String, String>): StateDeltaBlock {
        val patchString = patches.entries.sortedBy { it.key }.joinToString("&") { "${it.key}=${it.value}" }

        val payloadToHash = currentBlueprintHash + patchString

        val newHash = hash(payloadToHash.toByteArray(Charsets.UTF_8))

        val signature = sign(newHash)

        return StateDeltaBlock(
            patches = patches, previousHash = currentBlueprintHash, newHash = newHash, signature = signature
        )
    }
}