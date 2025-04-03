package com.lee.aihub.provider.builder

import com.lee.aihub.ModelCapability
import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

/**
 * OpenAI provider builder
 */
class OpenAIProviderBuilder(id: String) : ProviderBuilder(id) {

    init {
        source = "openai"
        baseUrl = "https://api.openai.com/v1/"
        isPaid = true
        tier = ModelTier.BIG
        capabilities = setOf(ModelCapability.CHAT, ModelCapability.CHAT_STREAMING, ModelCapability.TOOL_USE)
    }

    /**
     * Configure as a GPT-4o provider
     */
    fun gpt4o() {
        modelName("gpt-4o")
            .modelType("gpt-4o")
            .tier(ModelTier.BIG)
            .capabilities(
                ModelCapability.CHAT,
                ModelCapability.CHAT_STREAMING,
                ModelCapability.VISION,
                ModelCapability.TOOL_USE
            )
    }

    /**
     * Configure as a GPT-3.5 Turbo provider
     */
    fun gpt35Turbo() {
        modelName("gpt-3.5-turbo")
            .modelType("gpt-3.5-turbo")
            .tier(ModelTier.MEDIUM)
            .capabilities(
                ModelCapability.CHAT,
                ModelCapability.CHAT_STREAMING
            )
    }

    /**
     * Configure as a GPT-4 Turbo provider
     */
    fun gpt4Turbo() {
        modelName("gpt-4-turbo")
            .modelType("gpt-4-turbo")
            .tier(ModelTier.GIANT)
            .capabilities(
                ModelCapability.CHAT,
                ModelCapability.CHAT_STREAMING,
                ModelCapability.TOOL_USE
            )
    }


}