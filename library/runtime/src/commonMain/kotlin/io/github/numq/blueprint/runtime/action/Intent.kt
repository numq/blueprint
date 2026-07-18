package io.github.numq.blueprint.runtime.action

import kotlinx.serialization.Serializable
import kotlin.time.Clock

@Serializable
data class Intent(
    val id: String,
    val type: String,
    val nodeKey: String,
    val payload: IntentPayload = IntentPayload.Empty,
    val timestampMs: Long = Clock.System.now().toEpochMilliseconds(),
    val priority: IntentPriority = IntentPriority.NORMAL
)