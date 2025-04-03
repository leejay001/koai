package com.lee.aihub

import com.lee.net.httpclient.OpenAIConfig

val aiService by lazy {
    AIService(aiHub)
}

/**
 * Example of how to use the AIHub with your existing SDK
 */
class AIService(private val aiHub: AIHub) {
    /**
     * Get the next AI configuration for chat completion with text response
     */
    fun getNextChatCompletionConfig(
        isPaid: Boolean? = null,
        tier: ModelTier? = null,
        isImageSupported: Boolean = false,
    ): OpenAIConfig {
        val capabilities = buildSet {
            add(ModelCapability.CHAT)
            if (isImageSupported) add(ModelCapability.VISION)
        }

        val selector = ProviderSelector(
            isPaid = isPaid,
            tier = tier,
            capabilities = capabilities
        )

        val provider = aiHub.getProvider(selector)
        return provider.config
    }

    /**
     * Get the next AI configuration for chat completion with JSON response
     */
    fun getNextJsonChatCompletionConfig(
        isPaid: Boolean? = null,
        tier: ModelTier? = null,
        isImageSupported: Boolean = false,
    ): OpenAIConfig {
        return getNextChatCompletionConfig(isPaid, tier, isImageSupported)
    }

    /**
     * Get the next AI configuration for chat completion with tool use
     */
    fun getNextToolsCompletionConfig(
        isPaid: Boolean? = null,
        tier: ModelTier? = null,
        isImageSupported: Boolean = false,
    ): OpenAIConfig {
        val capabilities = buildSet {
            add(ModelCapability.CHAT)
            add(ModelCapability.TOOL_USE)
            if (isImageSupported) add(ModelCapability.VISION)
        }

        val selector = ProviderSelector(
            isPaid = isPaid,
            tier = tier,
            capabilities = capabilities
        )

        val provider = aiHub.getProvider(selector)
        return provider.config
    }

    /**
     * Get the next AI configuration for chat completion with reasoning
     */
    fun getNextReasoningCompletionConfig(
        isPaid: Boolean? = null,
        tier: ModelTier? = null,
    ): OpenAIConfig {
        val selector = ProviderSelector(
            isPaid = isPaid,
            tier = tier,
            capabilities = setOf(ModelCapability.CHAT, ModelCapability.REASONING)
        )

        val provider = aiHub.getProvider(selector)
        return provider.config
    }

    /**
     * 比如只想要deekseek r1 然后来源只想要 火山引擎和deepseek 官方
     * deepseek-r1
     *
     * 或者 llama-vision 所有渠道
     */
    fun getSpecificNextReasoningCompletionConfig(
        modelType: String,
        sources: Set<String>? = null
    ): Provider {
       return aiHub.getProvider(
            selector = ProviderSelector.specificModels(modelType = modelType, sources = sources)
        )
    }

    /**
     * Get a specific provider by ID
     */
    fun getSpecificProvider(id: String): OpenAIConfig? {
        return aiHub.getProviderById(id)?.config
    }
}