package com.lee.net.http

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.core.*

/**
 * Http request performer.
 */
interface HttpRequester : Closeable {

    /**
     * Perform an HTTP request and get a result.
     */
    suspend fun <T : Any> perform(info: TypeInfo, block: suspend (HttpClient) -> HttpResponse): T

    /**
     * Perform an HTTP request and get a result.
     *
     * Note: [HttpResponse] instance shouldn't be passed outside of [block].
     */
    suspend fun <T : Any> perform(
        builder: HttpRequestBuilder,
        block: suspend (response: HttpResponse) -> T
    )

}

