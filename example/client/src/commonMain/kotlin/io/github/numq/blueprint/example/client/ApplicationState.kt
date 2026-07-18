package io.github.numq.blueprint.example.client

import io.github.numq.blueprint.chain.BlueprintChain

data class ApplicationState(
    val chain: BlueprintChain = BlueprintChain(), val isLoading: Boolean = false, val error: String? = null
)