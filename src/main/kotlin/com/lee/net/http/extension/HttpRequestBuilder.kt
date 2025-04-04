package com.lee.net.http.extension

import com.lee.openai.api.core.RequestOptions
import io.ktor.client.plugins.*
import io.ktor.client.request.*

/**
 * Apply request options to the request builder.
 */
internal fun HttpRequestBuilder.requestOptions(requestOptions: RequestOptions? = null) {
    if (requestOptions == null) return
    requestOptions.headers.forEach { (key, value) -> headers.append(key, value) }
    requestOptions.urlParameters.forEach { (key, value) -> url.parameters.append(key, value) }
    requestOptions.timeout?.let { timeout ->
        timeout {
            timeout.connect?.let { connectTimeout -> connectTimeoutMillis = connectTimeout.inWholeMilliseconds }
            timeout.request?.let { requestTimeout -> requestTimeoutMillis = requestTimeout.inWholeMilliseconds }
            timeout.socket?.let { socketTimeout -> socketTimeoutMillis = socketTimeout.inWholeMilliseconds }
        }
    }
}
