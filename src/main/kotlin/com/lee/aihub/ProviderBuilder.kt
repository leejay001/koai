package com.lee.aihub

import com.lee.net.httpclient.OpenAIConfig
import com.lee.net.httpclient.OpenAIHost
import com.lee.net.httpclient.ProxyConfig
import com.lee.net.httpclient.Timeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Base builder for all providers
 */
abstract class ProviderBuilder(val id: String) {
    var isPaid: Boolean = false
    var tier: ModelTier = ModelTier.MEDIUM
    var capabilities: Set<ModelCapability> = setOf(ModelCapability.CHAT)
    var modelName: String? = null
    var modelType: String? = null
    var source: String = ""
    var apiKey: String = ""
    var baseUrl: String = ""
    var timeout: Duration = 100.seconds
    var proxy: ProxyConfig? = null
    var weight: Int = 1
    var tags: Map<String, String> = emptyMap()

    // 使用委托属性，确保只有在需要时才创建
    protected var _config: OpenAIConfig? = null

    // 允许外部设置预定义的配置
    fun config(config: OpenAIConfig): ProviderBuilder {
        _config = config
        return this
    }

    open fun build(): Provider {
        val finalConfig = _config ?: createOpenAIConfig()

        return Provider(
            id = id,
            source = source,
            isPaid = isPaid,
            tier = tier,
            capabilities = capabilities,
            modelName = modelName,
            modelType = modelType,
            config = finalConfig,
            weight = weight,
            tags = tags
        )
    }

    open fun openaiHost(): OpenAIHost = OpenAIHost.openai(
        baseUrl = baseUrl,
        authToken = apiKey
    )

    fun apiKey(apiKey: String): ProviderBuilder {
        this.apiKey = apiKey
        return this
    }

    fun baseUrl(baseUrl: String): ProviderBuilder {
        this.baseUrl = baseUrl
        return this
    }
    fun timeout(timeout: Duration): ProviderBuilder {
        this.timeout = timeout
        return this
    }

    fun modelName(modelName: String): ProviderBuilder {
        this.modelName = modelName
        return this
    }

    fun modelType(modelType: String): ProviderBuilder {
        this.modelType = modelType
        return this
    }

    fun source(source: String): ProviderBuilder {
        this.source = source
        return this
    }

    fun tier(tier: ModelTier): ProviderBuilder {
        this.tier = tier
        return this
    }

    fun proxy(proxy: ProxyConfig): ProviderBuilder {
        this.proxy = proxy
        return this
    }

    /**
     * Add chat capability
     */
    fun chat(): ProviderBuilder {
        capabilities = capabilities + ModelCapability.CHAT
        return this
    }

    /**
     * Add streaming capability
     */
    fun streaming(): ProviderBuilder {
        capabilities = capabilities + ModelCapability.CHAT_STREAMING
        return this
    }

    /**
     * Add vision capability
     */
    fun vision(): ProviderBuilder {
        capabilities = capabilities + ModelCapability.VISION
        return this
    }

    /**
     * Add reasoning capability
     */
    fun reasoning(): ProviderBuilder {
        capabilities = capabilities + ModelCapability.REASONING
        return this
    }

    /**
     * Add tool use capability
     */
    fun toolUse(): ProviderBuilder {
        capabilities = capabilities + ModelCapability.TOOL_USE
        return this
    }

    /**
     * Set capabilities from a list
     */
    fun capabilities(vararg capabilities: ModelCapability): ProviderBuilder {
        this.capabilities += capabilities.toSet()
        return this
    }

    /**
     * Set tags for this provider
     */
    fun tags(vararg pairs: Pair<String, String>) {
        this.tags = pairs.toMap()
    }



    protected fun createOpenAIConfig(): OpenAIConfig = OpenAIConfig(
        host = openaiHost(),
        timeout = Timeout(socket = timeout),
        proxy = proxy
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProviderBuilder) return false

        if (id != other.id) return false
        if (isPaid != other.isPaid) return false
        if (tier != other.tier) return false
        if (capabilities != other.capabilities) return false
        if (modelName != other.modelName) return false
        if (modelType != other.modelType) return false
        if (source != other.source) return false
        if (apiKey != other.apiKey) return false
        if (baseUrl != other.baseUrl) return false
        if (timeout != other.timeout) return false
        if (proxy != other.proxy) return false
        if (weight != other.weight) return false
        if (tags != other.tags) return false
        if (_config != other._config) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + isPaid.hashCode()
        result = 31 * result + tier.hashCode()
        result = 31 * result + capabilities.hashCode()
        result = 31 * result + (modelName?.hashCode() ?: 0)
        result = 31 * result + (modelType?.hashCode() ?: 0)
        result = 31 * result + source.hashCode()
        result = 31 * result + apiKey.hashCode()
        result = 31 * result + baseUrl.hashCode()
        result = 31 * result + timeout.hashCode()
        result = 31 * result + (proxy?.hashCode() ?: 0)
        result = 31 * result + weight
        result = 31 * result + tags.hashCode()
        result = 31 * result + (_config?.hashCode() ?: 0)
        return result
    }


}