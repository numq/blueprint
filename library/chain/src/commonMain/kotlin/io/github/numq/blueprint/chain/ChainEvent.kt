package io.github.numq.blueprint.chain

import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.action.StateDeltaBlock

sealed interface ChainEvent {
    data class Push(val blueprint: Blueprint) : ChainEvent

    data object Pop : ChainEvent

    data class Replace(val blueprint: Blueprint) : ChainEvent

    data class ApplyDeltas(val blocks: List<StateDeltaBlock>) : ChainEvent
}