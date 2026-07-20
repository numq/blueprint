package io.github.numq.blueprint.chain

import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.fp.Either
import io.github.numq.blueprint.runtime.fp.foldEither
import io.github.numq.blueprint.runtime.fp.left
import io.github.numq.blueprint.runtime.fp.right

data class BlueprintChain(
    val links: List<Blueprint> = emptyList(),
    val lastAction: Action = Action.IDLE,
    val strictMode: Boolean = true,
    val verifier: SignatureVerifier? = null
) {
    enum class Action {
        PUSH, POP, REPLACE, IDLE
    }

    val current: Blueprint? get() = links.lastOrNull()

    val canPop: Boolean get() = links.size > 1

    val size: Int get() = links.size

    fun reduce(event: ChainEvent): Either<ChainError, BlueprintChain> = when (event) {
        is ChainEvent.Push -> when {
            strictMode && links.isNotEmpty() -> when (val currentHash = current?.hash) {
                event.blueprint.previousHash -> copy(links = links + event.blueprint, lastAction = Action.PUSH).right()

                else -> ChainError.HashMismatch(
                    expected = currentHash ?: "null", actual = event.blueprint.previousHash
                ).left()
            }

            else -> copy(links = links + event.blueprint, lastAction = Action.PUSH).right()
        }

        is ChainEvent.Pop -> when {
            canPop -> copy(links = links.dropLast(1), lastAction = Action.POP).right()

            else -> ChainError.CannotPopEmptyChain.left()
        }

        is ChainEvent.Replace -> when {
            links.isEmpty() -> copy(links = listOf(event.blueprint), lastAction = Action.REPLACE).right()

            else -> {
                val targetPreviousHash = when {
                    links.size > 1 -> links[links.size - 2].hash

                    else -> null
                }

                when {
                    strictMode && targetPreviousHash != null && event.blueprint.previousHash != targetPreviousHash -> ChainError.HashMismatch(
                        expected = targetPreviousHash, actual = event.blueprint.previousHash
                    ).left()

                    else -> copy(links = links.dropLast(1) + event.blueprint, lastAction = Action.REPLACE).right()
                }
            }
        }

        is ChainEvent.ApplyDeltas -> when {
            links.isEmpty() || event.blocks.isEmpty() -> right()

            else -> event.blocks.foldEither(initial = this) { currentChain, block ->
                val currentBlueprint = currentChain.current ?: return@foldEither currentChain.right()

                when {
                    strictMode && currentBlueprint.hash != block.previousHash -> ChainError.HashMismatch(
                        expected = currentBlueprint.hash, actual = block.previousHash
                    ).left()

                    strictMode && verifier != null && !verifier.verify(
                        payload = block.newHash, signatureBase64 = block.signature
                    ) -> ChainError.SignatureVerificationFailed(blockHash = block.newHash).left()

                    else -> {
                        val updatedBlueprint = currentBlueprint.copy(
                            state = currentBlueprint.state + block.patches, hash = block.newHash
                        )

                        val newLinks = currentChain.links.toMutableList()

                        newLinks[newLinks.lastIndex] = updatedBlueprint

                        currentChain.copy(links = newLinks, lastAction = Action.IDLE).right()
                    }
                }
            }
        }
    }
}