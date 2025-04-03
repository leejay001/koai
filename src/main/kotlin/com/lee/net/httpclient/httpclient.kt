package com.lee.net.httpclient

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.util.*
import kotlin.time.DurationUnit

fun createHttpClient(config: OpenAIConfig, clientConfig: (HttpClientConfig<*>.() -> Unit)? = null) = lazy {

    HttpClient(CIO){

        engine {
            config.proxy?.let { proxyConfig ->
                proxy = when (proxyConfig) {
                    is ProxyConfig.Http -> ProxyBuilder.http(proxyConfig.url)
                    is ProxyConfig.Socks -> ProxyBuilder.socks(proxyConfig.host, proxyConfig.port)
                }
            }
        }

        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
                configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                registerModule(JavaTimeModule())
                registerModule(
                    KotlinModule.Builder()
                        .withReflectionCacheSize(512)
                        .configure(KotlinFeature.NullToEmptyCollection, false)
                        .configure(KotlinFeature.NullToEmptyMap, false)
                        .configure(KotlinFeature.NullIsSameAsDefault, false)
                        .configure(KotlinFeature.SingletonSupport, true)
                        .configure(KotlinFeature.StrictNullChecks, false)
                        .build()
                )
            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL // 确保打印出请求和响应的主体
        }


        install(HttpTimeout) {
            config.timeout.socket?.let { socketTimeout ->
                socketTimeoutMillis = socketTimeout.toLong(DurationUnit.MILLISECONDS)
            }
            config.timeout.connect?.let { connectTimeout ->
                connectTimeoutMillis = connectTimeout.toLong(DurationUnit.MILLISECONDS)
            }
            config.timeout.request?.let { requestTimeout ->
                requestTimeoutMillis = requestTimeout.toLong(DurationUnit.MILLISECONDS)
            }
        }

        install(HttpRequestRetry) {
            maxRetries = config.retry.maxRetries
            // retry on rate limit error.
            retryIf { _, response -> response.status.value.let { it == 429 } }
            exponentialDelay(config.retry.base, config.retry.maxDelay.inWholeMilliseconds)
        }

        defaultRequest {
            url(config.host.baseUrl)

            if (config.host.authToken.isNotEmpty()){
                val tokenValue = "Bearer ${config.host.authToken}"
                if (headers.contains(HttpHeaders.Authorization)){
                    headers.remove(HttpHeaders.Authorization)
                }
                headers.append(HttpHeaders.Authorization, tokenValue)
            }

            config.host.queryParams.onEach { (key, value) -> url.parameters.appendIfNameAbsent(key, value) }
            config.host.headers.onEach { (key, value) -> headers.appendIfNameAbsent(key, value) }
            config.organization?.let { organization -> headers.append("OpenAI-Organization", organization) }
        }

        clientConfig?.invoke(this)
    }
}

val safeJackson by lazy {
    jacksonObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
        configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        registerModule(JavaTimeModule())
        enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, true)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )

        findAndRegisterModules()

    }
}
