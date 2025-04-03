package com.lee.net.httpclient

import com.lee.openai.anotations.AzureAIDsl
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


public class OpenAIConfig(
    public val host: OpenAIHost,
    public val timeout: Timeout = Timeout(socket = 30.seconds),
    public val organization: String? = null,
    public val proxy: ProxyConfig? = null,
    public val retry: RetryStrategy = RetryStrategy()
)

/**
 * Specifies the retry strategy
 *
 * @param maxRetries the maximum amount of retries to perform for a request
 * @param base retry base value
 * @param maxDelay max retry delay
 */
public class RetryStrategy(
    public val maxRetries: Int = 3,
    public val base: Double = 2.0,
    public val maxDelay: Duration = 60.seconds,
)


/** Proxy configuration. */
public sealed interface ProxyConfig {

    /** Creates an HTTP proxy from [url]. */
    public class Http(public val url: String) : ProxyConfig

    /** Create socks proxy from [host] and [port]. */
    public class Socks(public val host: String, public val port: Int) : ProxyConfig
}

/**
 * 适配 azure 和 openai
 *
 * azure类似的形式：
 * ```Kotlin
 * val client by getHttpClient{
 *             defaultRequest {
 *                 url("https://xx.openai.azure.com/openai/deployments/xxxx/")
 *                 val query = mapOf("api-version" to "2024-02-15-preview")
 *                 query.onEach { (key, value) -> url.parameters.appendIfNameAbsent(key, value) }
 *             }
 *         }
 * ```
 * 这个类应该是得到baseUrl
 *
 */
public class OpenAIHost(

    /**
     * Base URL configuration.
     * This is the root URL that will be used for all API requests to OpenAI.
     * The URL can include a base path, but in that case, the base path should always end with a `/`.
     * For example, a valid base URL would be "https://api.openai.com/v1/"
     */
    public val baseUrl: String,

    /**
     * Additional query parameters to be appended to all API requests to OpenAI.
     * These can be used to provide additional configuration or context for the API requests.
     */
    @AzureAIDsl
    public val queryParams: HashMap<String, String> = hashMapOf(),

    /**
     * like auth header or others, for azure auth
     */
    @AzureAIDsl
    public val headers: HashMap<String, String> = hashMapOf(),

    public val authToken: String = ""

) {

    public companion object {
        /**
         * A pre-configured instance of [OpenAIHost] with the base URL set as `https://api.openai.com/v1/`.
         */
        public fun openai(
            baseUrl: String = "https://api.openai.com/v1/",
            authToken: String
        ): OpenAIHost = OpenAIHost(
            baseUrl = baseUrl,
            authToken = authToken
        )

        /**
         * Creates an instance of [OpenAIHost] configured for Azure hosting with the given resource name, deployment ID,
         * and API version.
         *
         * @param resourceName The name of your Azure OpenAI Resource.
         * @param deploymentId The name of your model deployment.
         * @param apiVersion The API version to use for this operation. This parameter should follow the YYYY-MM-DD format.
         */
        public fun azure(
            resourceName: String,
            deploymentId: String,
            apiKey: String,
            apiVersion: String = latestAzureApiVersionTTS,
            moreHeader: (HashMap<String, String>.() -> Unit)? = null
        ): OpenAIHost =
            OpenAIHost(
                baseUrl = "https://$resourceName.openai.azure.com/openai/deployments/$deploymentId/",
                queryParams = hashMapOf("api-version" to apiVersion),
                headers = hashMapOf("api-key" to apiKey).apply {
                    moreHeader?.invoke(this)
                }
            )

        private const val latestAzureApiVersionTTS = "2024-02-15-preview"
    }
}