package io.github.numq.blueprint.runtime.component

import io.github.numq.blueprint.runtime.component.layout.LayoutAlignment
import io.github.numq.blueprint.runtime.component.layout.LayoutArrangement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface LayoutPayload : ComponentPayload {
    @Serializable
    @SerialName("layout.Box")
    data class Box(
        val contentAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START
    ) : LayoutPayload

    @Serializable
    @SerialName("layout.Column")
    data class Column(
        val verticalArrangement: LayoutArrangement = LayoutArrangement.START,
        val horizontalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
        val spacing: Float = 0f
    ) : LayoutPayload

    @Serializable
    @SerialName("layout.Row")
    data class Row(
        val horizontalArrangement: LayoutArrangement = LayoutArrangement.START,
        val verticalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
        val spacing: Float = 0f
    ) : LayoutPayload

    @Serializable
    @SerialName("layout.Spacer")
    data class Spacer(val size: Float = 0f) : LayoutPayload

    @Serializable
    @SerialName("layout.LazyColumn")
    data class LazyColumn(
        val verticalArrangement: LayoutArrangement = LayoutArrangement.START,
        val horizontalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
        val contentPadding: Float = 0f,
        val reversed: Boolean = false,
        val onLoadMoreIntentId: String? = null,
        val loadMoreThreshold: Int = 3
    ) : LayoutPayload

    @Serializable
    @SerialName("layout.LazyRow")
    data class LazyRow(
        val horizontalArrangement: LayoutArrangement = LayoutArrangement.START,
        val verticalAlignment: LayoutAlignment = LayoutAlignment.ALIGN_START,
        val contentPadding: Float = 0f,
        val reversed: Boolean = false,
        val onLoadMoreIntentId: String? = null,
        val loadMoreThreshold: Int = 3
    ) : LayoutPayload
}