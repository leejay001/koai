package com.lee.aihub

import com.lee.aihub.provider.builder.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * Builder DSL for AIHub configuration
 */
class AIHubBuilder {
    public val providers = mutableListOf<Provider>()
    var healthCheckInterval: Duration = 30.seconds
    var unhealthyRecheckInterval: Duration = 2.minutes
    var enableHealthChecks: Boolean = true
    var healthChecker: (suspend (Provider) -> Boolean)? = null

    /**
     * Add an OpenAI provider with a configuration DSL
     */
    fun openai(
        apiKey: String,
        id: String = "openai-${System.currentTimeMillis()}",
        block: OpenAIProviderBuilder.() -> Unit
    ) {
        val builder = OpenAIProviderBuilder(id).apply {
            this.apiKey = apiKey
        }
        builder.block()
        providers.add(builder.build())
    }

    /**
     * Add an OpenRouter provider with a configuration DSL
     */
    fun openRouter(
        apiKey: String,
        id: String = "openrouter-${System.currentTimeMillis()}",
        block: OpenRouterProviderBuilder.() -> Unit
    ) {
        val builder = OpenRouterProviderBuilder(id).apply {
            this.apiKey = apiKey
        }
        builder.block()
        providers.add(builder.build())
    }

    /**
     * Add a Claude provider with a configuration DSL
     */
    fun claude(
        id: String = "claude-${System.currentTimeMillis()}",
        block: ClaudeProviderBuilder.() -> Unit
    ) {
        val builder = ClaudeProviderBuilder(id)
        builder.block()
        providers.add(builder.build())
    }

    /**
     * Add a Silicon Flow provider with a configuration DSL
     */
    fun siliconFlow(
        id: String = "siliconflow-${System.currentTimeMillis()}",
        block: SiliconFlowProviderBuilder.() -> Unit
    ) {
        val builder = SiliconFlowProviderBuilder(id)
        builder.block()
        providers.add(builder.build())
    }

    /**
     * Add multiple Silicon Flow providers with the same configuration
     */
    fun siliconFlowBulk(
        apiKeys: List<String>,
        block: SiliconFlowProviderBuilder.() -> Unit = {}
    ) {
        apiKeys.forEachIndexed { index, apiKey ->
            val builder = SiliconFlowProviderBuilder("siliconflow-$index")
            builder.block()
            builder.apiKey = apiKey
            providers.add(builder.build())
        }
    }


    fun deepSeek(
        apiKey: String,
        id: String = "deepseek-${System.currentTimeMillis()}",
        block: DeepSeekProviderBuilder.() -> Unit
    ) {
        val builder = DeepSeekProviderBuilder(id).apply {
            this.apiKey = apiKey
        }
        builder.block()
        providers.add(builder.build())
    }

    fun deepSeekBulk(
        apiKeys: List<String>,
        block: DeepSeekProviderBuilder.() -> Unit = {}
    ) {
        apiKeys.forEachIndexed { index, apiKey ->
            val builder = DeepSeekProviderBuilder("deepseek-$index")
            builder.block()
            builder.apiKey = apiKey
            providers.add(builder.build())
        }
    }

    fun volcanoEngine(
        apiKey: String,
        id: String = "volcanoengine-${System.currentTimeMillis()}",
        block: VolcanoEngineProviderBuilder.() -> Unit
    ) {
        val builder = VolcanoEngineProviderBuilder(id).apply {
            this.apiKey = apiKey
        }
        builder.block()
        providers.add(builder.build())
    }

    fun volcanoEngineBulk(
        apiKeys: List<String>,
        block: VolcanoEngineProviderBuilder.() -> Unit = {}
    ) {
        apiKeys.forEachIndexed { index, apiKey ->
            val builder = VolcanoEngineProviderBuilder("volcanoengine-$index")
            builder.block()
            builder.apiKey = apiKey
            providers.add(builder.build())
        }
    }

    /**
     * Add a Nvidia provider
     */
    fun nvidia(
        apiKey: String = "",
        id: String = "nvidia-${System.currentTimeMillis()}",
        block: NvidiaProviderBuilder.() -> Unit
    ) {
        val builder = NvidiaProviderBuilder(id).apply {
            this.apiKey = apiKey
        }
        builder.block()
        providers.add(builder.build())
    }

    fun nvidiaBulk(
        apiKeys: List<String>,
        block: NvidiaProviderBuilder.() -> Unit = {}
    ) {
        apiKeys.forEachIndexed { index, apiKey ->
            val builder = NvidiaProviderBuilder("nvidia-$index")
            builder.block()
            builder.apiKey = apiKey
            providers.add(builder.build())
        }
    }

    fun groq(
        apiKey: String = "",
        id: String = "groq-${System.currentTimeMillis()}",
        block: GroqProviderBuilder.() -> Unit
    ) {
        val builder = GroqProviderBuilder(id).apply {
            this.apiKey = apiKey
        }
        builder.block()
        providers.add(builder.build())
    }

    fun groqBulk(
        apiKeys: List<String>,
        block: GroqProviderBuilder.() -> Unit = {}
    ) {
        apiKeys.forEachIndexed { index, apiKey ->
            val builder = GroqProviderBuilder("groq-$index")
            builder.block()
            builder.apiKey = apiKey
            providers.add(builder.build())
        }
    }

    inline fun <reified T : ProviderBuilder> create(
        apiKey: String = "",
        id: String = "${T::class.simpleName}-${System.currentTimeMillis()}",
        block: T.() -> Unit
    ) {
        // 使用反射创建 T 的实例，假设 T 有接收 id 的构造函数
        val builder = T::class.java.getDeclaredConstructor(String::class.java).newInstance(id)
        // 设置 apiKey（假设 T 有 apiKey 属性）
        builder.apiKey = apiKey
        // 应用配置块
        builder.apply(block)
        // 构建 Provider 并添加到集合
        providers.add(builder.build())
    }

    inline fun <reified T : ProviderBuilder> creates(
        apiKeys: List<String>,
        id: String,
        block: T.() -> Unit
    ) {
        apiKeys.forEachIndexed { index, apiKey ->
            // 使用反射创建 T 的实例，假设 T 有接收 id 的构造函数
            val builder = T::class.java.getDeclaredConstructor(String::class.java).newInstance("$id-$index")
            // 设置 apiKey（假设 T 有 apiKey 属性）
            builder.apiKey = apiKey
            // 应用配置块
            builder.apply(block)
            // 构建 Provider 并添加到集合
            providers.add(builder.build())
        }
    }


    fun grok(
        apiKey: String = "",
        id: String = "groq-${System.currentTimeMillis()}",
        block: GrokProviderBuilder.() -> Unit
    ) = create<GrokProviderBuilder>(apiKey, id, block)

    fun novita(
        apiKey: String = "",
        id: String = "novita-${System.currentTimeMillis()}",
        block: NovitaProviderBuilder.() -> Unit
    ) = create<NovitaProviderBuilder>(apiKey, id, block)

    fun novitaBulk(
        apiKeys: List<String>,
        block: NovitaProviderBuilder.() -> Unit = {}
    ) = creates<NovitaProviderBuilder>(apiKeys = apiKeys, "novita", block)


    /**
     * Add providers from another config source
     */
    fun addProviders(newProviders: List<Provider>) {
        providers.addAll(newProviders)
    }

    /**
     * Add a custom provider with raw configuration
     */
    fun provider(provider: Provider) {
        providers.add(provider)
    }

    /**
     * Configure the health check system
     */
    fun healthCheck(
        interval: Duration = 30.seconds,
        unhealthyInterval: Duration = 2.minutes,
        enable: Boolean = true,
        block: (suspend (Provider) -> Boolean)? = null
    ) {
        healthCheckInterval = interval
        unhealthyRecheckInterval = unhealthyInterval
        enableHealthChecks = enable
        healthChecker = block
    }

    internal fun build(): AIHubConfig = AIHubConfig(
        providers = providers,
        healthCheckInterval = healthCheckInterval,
        unhealthyRecheckInterval = unhealthyRecheckInterval,
        enableHealthChecks = enableHealthChecks,
        healthChecker = healthChecker
    )
}
