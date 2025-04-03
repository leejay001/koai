package com.lee.aihub.provider.builder

import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

class GrokProviderBuilder(id: String) : ProviderBuilder(id) {

    init {
        source = "grok"
        baseUrl = "https://api.x.ai/v1/"
        isPaid = true
        tier = ModelTier.MEDIUM
    }

    fun grok2Vision() =
        modelName("grok-2-vision-latest")
            .modelType("grok-2-vision")
            .tier(ModelTier.MEDIUM)
            .chat()
            .streaming()
            .vision()

}