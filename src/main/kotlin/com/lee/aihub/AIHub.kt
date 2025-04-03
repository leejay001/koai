package com.lee.aihub

import com.lee.net.httpclient.ProxyConfig
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.toJavaDuration

val aiHub by lazy {
    AIHub.create {
        openai(apiKey = "sk-XX") {
            gpt4o()
        }

        openai(apiKey = "sk-XX") {
            gpt35Turbo()
        }

        volcanoEngineBulk(
            listOf("apiKey1", "apiKey2")
        ) {
            deepSeekR1()
        }

        deepSeekBulk(listOf("apiKey1", "apiKey2")) {
            deepSeekR1()
        }

        nvidiaBulk(
            listOf("apiKey1", "apiKey2")
        ) {
            deepSeekR1()
        }

        nvidiaBulk(listOf("apiKey1", "apiKey2")) {
            llama3_2_90B()
        }

        openRouter("apiKey") {
            proxy(ProxyConfig.Http(url = "http://xxx.x.x.x:port/"))
            gpt4o()
        }

        openRouter("apiKey") {
            llama_3_2_90B()
        }

        openRouter("apiKey") {
            gemini_2_flash_lite()
        }

        groqBulk(
            listOf(
                "apiKey1", "apiKey2", "apiKey3"
            )
        ) {
            llama3_2_90B()
        }

        grok("apiKey") {
            grok2Vision()
        }

        novitaBulk(
            apiKeys = listOf("apiKey")
        ) {
            deepseek()
        }

    }
}

/**
 * AIHub - Smart AI Provider Management
 *
 * A sophisticated orchestration layer for managing multiple AI providers with
 * intelligent routing, health monitoring, and configurable fallbacks.
 */
class AIHub private constructor(
    private val config: AIHubConfig
) {
    private val logger = LoggerFactory.getLogger(AIHub::class.java)
    private val providerMap = ConcurrentHashMap<String, Provider>()
    private val healthStatus = ConcurrentHashMap<String, ProviderHealth>()
    private val counters = ConcurrentHashMap<String, AtomicInteger>()
    private val healthCheckScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        registerProviders()
        startHealthChecks()
        logger.info("AIHub initialized with ${providerMap.size} providers")
    }

    /**
     * Gets the next appropriate provider for a request based on selection criteria
     */
    fun getProvider(selector: ProviderSelector = ProviderSelector()): Provider {
        val providers = findMatchingProviders(selector)
            .filter { healthStatus[it.id]?.isHealthy ?: false }

        if (providers.isEmpty()) {
            throw NoSuitableProviderException("No matching healthy providers found for $selector")
        }

        return when (selector.strategy) {
            SelectionStrategy.SPECIFIC -> providers.first()
            SelectionStrategy.WEIGHTED_RANDOM -> selectWeightedRandom(providers)
            SelectionStrategy.ROUND_ROBIN -> selectRoundRobin(providers)
        }
    }

    /**
     * Gets a provider specifically by its ID
     */
    fun getProviderById(id: String): Provider? {
        val provider = providerMap[id]
        return if (provider != null && (healthStatus[id]?.isHealthy ?: false)) {
            provider
        } else null
    }

    /**
     * List all registered providers, optionally filtered by status
     */
    fun listProviders(onlyHealthy: Boolean = false): List<Provider> {
        return providerMap.values.filter {
            !onlyHealthy || (healthStatus[it.id]?.isHealthy ?: false)
        }.toList()
    }

    /**
     * Gets all provider health statuses
     */
    fun getHealthStatuses(): Map<String, ProviderHealth> = healthStatus.toMap()

    /**
     * Manually update the health status of a provider
     */
    fun updateProviderHealth(id: String, isHealthy: Boolean) {
        val provider = providerMap[id] ?: return
        val current = healthStatus[id] ?: ProviderHealth(isHealthy, Instant.now())
        healthStatus[id] = current.copy(
            isHealthy = isHealthy,
            lastUpdated = Instant.now(),
            errorCount = if (isHealthy) 0 else current.errorCount + 1
        )
        logger.info("Provider $id health updated to $isHealthy")
    }

    /**
     * Shut down the AIHub cleanly
     */
    fun shutdown() {
        healthCheckScope.cancel()
        logger.info("AIHub shutdown complete")
    }

    private fun registerProviders() {
        config.providers.forEach { provider ->
            providerMap[provider.id] = provider
            healthStatus[provider.id] = ProviderHealth(true, Instant.now())
        }
    }

    private fun startHealthChecks() {
        if (!config.enableHealthChecks) return

        healthCheckScope.launch {
            while (isActive) {
                try {
                    checkProvidersHealth()
                    delay(config.healthCheckInterval.toJavaDuration().toMillis())
                } catch (e: Exception) {
                    logger.error("Error during health check", e)
                }
            }
        }
    }

    private suspend fun checkProvidersHealth() {
        val now = Instant.now()
        healthStatus.forEach { (id, status) ->
            val timeSinceUpdate = java.time.Duration.between(status.lastUpdated, now).seconds

            // Check unhealthy providers less frequently
            if (!status.isHealthy && timeSinceUpdate > config.unhealthyRecheckInterval.inWholeSeconds) {
                checkProviderHealth(id)
            }
            // Regular health check for healthy providers
            else if (status.isHealthy && timeSinceUpdate > config.healthCheckInterval.inWholeSeconds) {
                checkProviderHealth(id)
            }
        }
    }

    private suspend fun checkProviderHealth(id: String) {
        val provider = providerMap[id] ?: return
        val isHealthy = config.healthChecker?.invoke(provider) ?: true

        val current = healthStatus[id] ?: return
        healthStatus[id] = current.copy(
            isHealthy = isHealthy,
            lastUpdated = Instant.now(),
            errorCount = if (isHealthy) 0 else current.errorCount + 1
        )

        if (current.isHealthy != isHealthy) {
            logger.info("Provider $id health changed to $isHealthy")
        }
    }

    private fun findMatchingProviders(selector: ProviderSelector): List<Provider> {
        // If a specific provider ID is requested, return just that one
        if (selector.providerId != null) {
            return listOfNotNull(providerMap[selector.providerId])
        }

        return providerMap.values.filter { provider ->
            // Filter by tier if specified
            (selector.tier == null || provider.tier == selector.tier) &&

                    // Filter by paid status if specified
                    (selector.isPaid == null || provider.isPaid == selector.isPaid) &&

                    // Filter by source if specified
                    (selector.source == null || provider.source == selector.source) &&

                    // Filter by sources if specified
                    (selector.sources == null || provider.source in selector.sources) &&


                    // Filter by capabilities - provider must have ALL requested capabilities
                    selector.capabilities.all { it in provider.capabilities } &&
                    (selector.modelType == null || provider.modelType == selector.modelType) &&

                    // Filter by tags - all specified tags must match
                    selector.tags.all { (key, value) -> provider.tags[key] == value }
        }
    }

    private fun selectWeightedRandom(providers: List<Provider>): Provider {
        val totalWeight = providers.sumOf { it.weight }
        val random = (0 until totalWeight).random()

        var cumulativeWeight = 0
        for (provider in providers) {
            cumulativeWeight += provider.weight
            if (random < cumulativeWeight) {
                return provider
            }
        }

        // Fallback (shouldn't happen)
        return providers.first()
    }

    /**
     * 使用轮询(Round Robin)策略从提供者列表中选择一个提供者
     *
     * 该方法的巧妙之处在于为每一个不同的提供者组合维护单独的计数器，
     * 确保相同条件下的负载均衡始终保持一致性和连续性
     *
     * 隔离性：不同筛选条件得到的提供者集合拥有各自独立的轮询计数，互不干扰
     * 一致性：即使提供者列表以不同顺序传入，通过排序操作也能确保产生相同的键，从而保持轮询的连续性
     * 高效性：只为实际使用的提供者组合创建计数器，节省内存
     * 并发安全：使用ConcurrentHashMap和AtomicInteger确保在高并发环境下的正确性
     * 自适应性：当提供者集合变化时(如某个提供者变为不健康)，会自动创建新的轮询计数器
     * 无状态服务支持：即使在分布式、无状态服务中，只要请求带有相同的筛选条件，也能实现一定程度的"粘性"轮询
     *
     */
    private fun selectRoundRobin(providers: List<Provider>): Provider {
        // 为当前提供者集合创建一个稳定的唯一键
        // 通过先排序再连接，确保相同的提供者集合(无论初始顺序如何)会产生相同的键
        val key = providers.sortedBy { it.id }.joinToString("-") { it.id }

        // 根据键获取对应的计数器，如果不存在则创建新的计数器
        // 这确保了每一个特定的提供者组合都有自己独立的轮询计数器
        val counter = counters.computeIfAbsent(key) { AtomicInteger(0) }

        // 原子地递增计数器并获取递增前的值，然后对提供者数量取模
        // 确保索引总是在有效范围内，实现经典的轮询(Round Robin)算法
        val index = (counter.getAndIncrement() % providers.size)

        // 返回当前轮次选中的提供者
        return providers[index]
    }

    companion object {
        /**
         * Create a new AIHub instance with a configuration DSL
         */
        fun create(block: AIHubBuilder.() -> Unit): AIHub {
            val builder = AIHubBuilder()
            builder.block()
            return AIHub(builder.build())
        }
    }

    /**
     * Exception thrown when no suitable provider is available
     */
    class NoSuitableProviderException(message: String) : RuntimeException(message)
}