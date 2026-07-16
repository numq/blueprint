package io.github.numq.blueprint.runtime.action

import io.github.numq.blueprint.runtime.Blueprint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Effect {
    @Serializable
    @SerialName("navigation")
    data class Navigation(
        val blueprint: Blueprint? = null, @SerialName("navigation_type") val type: Type = Type.PUSH
    ) : Effect {
        @Serializable
        enum class Type {
            @SerialName("PUSH")
            PUSH,

            @SerialName("REPLACE")
            REPLACE,

            @SerialName("POP")
            POP
        }
    }

    @Serializable
    @SerialName("snackbar")
    data class Snackbar(
        val message: String,
        val isError: Boolean = false,
        val durationMs: Int = 3_000,
        val actionLabel: String? = null,
        val actionIntent: Intent? = null
    ) : Effect

    @Serializable
    @SerialName("dialog")
    data class Dialog(val title: String, val message: String, val actions: List<DialogAction> = emptyList()) : Effect {
        @Serializable
        data class DialogAction(val label: String, val intent: Intent, val isPrimary: Boolean = false)
    }
}