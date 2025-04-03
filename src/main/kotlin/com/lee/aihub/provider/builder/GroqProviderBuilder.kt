package com.lee.aihub.provider.builder

import com.lee.aihub.ModelCapability
import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

class GroqProviderBuilder(id: String) : ProviderBuilder(id){

    init {
        source = "groq"
        baseUrl = "https://api.groq.com/openai/v1/"
        isPaid = false
        tier = ModelTier.MEDIUM
        capabilities = setOf(ModelCapability.CHAT)
    }

    fun llama3_2_90B() =
        modelName("llama-3.2-90b-vision-preview")
            .modelType("llama-3.2-90b-vision")
            .tier(ModelTier.MEDIUM)
            .streaming()
            .chat()
            .vision()
}