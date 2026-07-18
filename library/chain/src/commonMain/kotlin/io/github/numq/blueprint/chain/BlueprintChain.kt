package io.github.numq.blueprint.chain

import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.action.StateDeltaBlock

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

    fun push(blueprint: Blueprint): BlueprintChain {
        verifyCryptographicLink(blueprint = blueprint)

        return copy(links = links + blueprint, lastAction = Action.PUSH)
    }

    fun pop(): BlueprintChain {
        if (!canPop) return this

        return copy(links = links.dropLast(1), lastAction = Action.POP)
    }

    fun replace(blueprint: Blueprint): BlueprintChain {
        if (links.isEmpty()) {
            return copy(links = listOf(blueprint), lastAction = Action.REPLACE)
        }

        val targetPreviousHash = if (links.size > 1) links[links.size - 2].hash else null

        if (strictMode && targetPreviousHash != null && blueprint.previousHash != targetPreviousHash) {
            throw ChainException("Replace Failed! Expected previous_hash: $targetPreviousHash, but got: ${blueprint.previousHash}")
        }

        return copy(links = links.dropLast(1) + blueprint, lastAction = Action.REPLACE)
    }

    fun applyDeltaBlocks(deltaBlocks: List<StateDeltaBlock>): BlueprintChain {
        if (links.isEmpty() || deltaBlocks.isEmpty()) return this

        var currentBlueprint = links.last()

        for (block in deltaBlocks) {
            if (strictMode && currentBlueprint.hash != block.previousHash) {
                throw ChainException("State Sync Error! Expected state hash: ${currentBlueprint.hash}, but delta block targets: ${block.previousHash}.")
            }

            if (strictMode && verifier != null) {
                val isValid = verifier.verify(block.newHash, block.signature)

                if (!isValid) {
                    throw ChainException("Signature Verification Failed! Delta block signature is invalid. Data may be tampered.")
                }
            }

            currentBlueprint = currentBlueprint.copy(
                state = currentBlueprint.state + block.patches, hash = block.newHash
            )
        }

        val newLinks = links.toMutableList()

        newLinks[newLinks.lastIndex] = currentBlueprint

        return copy(links = newLinks, lastAction = Action.IDLE)
    }

    private fun verifyCryptographicLink(blueprint: Blueprint) {
        if (!strictMode || links.isEmpty()) return

        val currentHash = current?.hash

        if (blueprint.previousHash != currentHash) {
            throw ChainException("Man-in-the-Middle attack detected or server desync. Expected previous_hash: $currentHash, but got: ${blueprint.previousHash}")
        }
    }
}