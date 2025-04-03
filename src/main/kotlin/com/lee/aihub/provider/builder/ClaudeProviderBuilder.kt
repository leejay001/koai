package com.lee.aihub.provider.builder

import com.lee.aihub.ModelCapability
import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder


class ClaudeProviderBuilder(id: String) : ProviderBuilder(id) {

    init {
        source = "claude"
        baseUrl = "https://api.anthropic.com/v1/"
        isPaid = true
        tier = ModelTier.BIG
        capabilities = setOf(ModelCapability.CHAT, ModelCapability.CHAT_STREAMING)
    }

    /**
     * Configure as Claude 3 Opus
     */
    fun opus() {
        modelName = "claude-3-opus-20240229"
        tier = ModelTier.GIANT
        capabilities = setOf(
            ModelCapability.CHAT,
            ModelCapability.CHAT_STREAMING,
            ModelCapability.VISION,
            ModelCapability.REASONING
        )
    }

    /**
     * Configure as Claude 3 Sonnet
     */
    fun sonnet() {
        modelName = "claude-3-sonnet-20240229"
        tier = ModelTier.BIG
        capabilities = setOf(
            ModelCapability.CHAT,
            ModelCapability.CHAT_STREAMING,
            ModelCapability.VISION
        )
    }

    /**
     * Configure as Claude 3 Haiku
     */
    fun haiku() {
        modelName = "claude-3-haiku-20240307"
        tier = ModelTier.MEDIUM
        capabilities = setOf(
            ModelCapability.CHAT,
            ModelCapability.CHAT_STREAMING,
            ModelCapability.VISION
        )
    }
}