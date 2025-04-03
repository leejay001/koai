package com.lee.aihub

import com.lee.net.httpclient.OpenAIConfig
import java.time.Instant
import kotlin.time.Duration

/**
 * AIHub configuration
 */
data class AIHubConfig(
    val providers: List<Provider>,
    val healthCheckInterval: Duration,
    val unhealthyRecheckInterval: Duration,
    val enableHealthChecks: Boolean,
    val healthChecker: (suspend (Provider) -> Boolean)?
)

/**
 * Provider definition with all metadata
 */
data class Provider(
    val id: String,
    val source: String,
    val isPaid: Boolean,
    val tier: ModelTier,
    val capabilities: Set<ModelCapability>,
    val modelName: String?,
    /*
     * 模型的通用类型标识（如"gpt-4o"、"claude-3-opus"等）
     */
    val modelType: String?,
    val config: OpenAIConfig,
    val weight: Int = 1,
    val tags: Map<String, String> = emptyMap()
)

/**
 * Provider health status
 */
data class ProviderHealth(
    val isHealthy: Boolean,
    val lastUpdated: Instant,
    val errorCount: Int = 0,
    val successCount: Int = 0
)

/**
 * Model capability types
 */
enum class ModelCapability {
    CHAT,                // Basic chat
    CHAT_STREAMING,      // Streaming chat
    VISION,              // Vision processing
    EMBEDDINGS,          // Embeddings generation
    REASONING,           // Step-by-step reasoning
    IMAGE_GENERATION,    // Image generation
    TOOL_USE             // Using tools/functions
}

/**
 * Model tiers (quality/size)
 */
enum class ModelTier {
    MEDIUM,  // Lower tier (GPT-3.5, Haiku, etc.)
    BIG,     // Mid tier (GPT-4o, Sonnet, etc.)
    GIANT    // Top tier (GPT-4, Opus, etc.)
}

/**
 * Provider selection strategy
 */
enum class SelectionStrategy {
    SPECIFIC,       // Use a specific provider by ID
    WEIGHTED_RANDOM, // Random selection weighted by provider weights
    ROUND_ROBIN     // Round-robin among suitable providers
}

/**
 * Criteria for selecting a provider
 */
data class ProviderSelector(
    val providerId: String? = null,
    val tier: ModelTier? = null,
    val capabilities: Set<ModelCapability> = emptySet(),
    val isPaid: Boolean? = null,
    val source: String? = null,
    val sources: Set<String>? = null,
    // 按模型类型筛选
    val modelType: String? = null,
    val tags: Map<String, String> = emptyMap(),
    val strategy: SelectionStrategy = SelectionStrategy.ROUND_ROBIN
) {
    companion object {
        // Factory methods for common selectors
        /**
         * 直接某个provider，通过id查找
         */
        fun specificProvider(id: String) = ProviderSelector(providerId = id, strategy = SelectionStrategy.SPECIFIC)

        /**
         * 所有渠道的某一个模型，会返回很多provider，有着同一个modelType,比如都是gpt-4o,都是deep-seek-r1, 都是meta-llama2-vision
         * 还可以再筛选渠道
         */
        fun specificModels(modelType: String,sources: Set<String>?) = ProviderSelector(modelType = modelType, sources = sources)

        fun reasoning() = ProviderSelector(capabilities = setOf(ModelCapability.REASONING))

        fun vision() = ProviderSelector(capabilities = setOf(ModelCapability.VISION))

        fun free() = ProviderSelector(isPaid = false)

        fun paid() = ProviderSelector(isPaid = true)

        fun tier(tier: ModelTier) = ProviderSelector(tier = tier)
    }
}