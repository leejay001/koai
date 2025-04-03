package com.lee.aihub.provider.builder

import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

/**
 * OpenRouter provider builder
 */
class OpenRouterProviderBuilder(id: String) : ProviderBuilder(id) {

    init {
        source = "openrouter"
        baseUrl = "https://openrouter.ai/api/v1/"
        isPaid = true
        tier = ModelTier.BIG
    }


    fun gpt4o(): ProviderBuilder =
        modelName("openai/gpt-4o-2024-11-20")
            .modelType("gpt-4o")
            .tier(ModelTier.BIG)
            .chat()
            .streaming()
            .vision()


    fun llama_3_2_90B() =
        modelName("meta-llama/llama-3.2-90b-vision-instruct:free")
            .modelType("llama-3.2-90b-vision")
            .tier(ModelTier.MEDIUM)
            .chat()
            .streaming()
            .vision()

    fun gemini_2_flash_lite() =
        modelName("google/gemini-2.0-flash-lite-001")
            .modelType("gemini_2_flash_lite")
            .tier(ModelTier.MEDIUM)
            .chat()
            .streaming()
            .vision()
}