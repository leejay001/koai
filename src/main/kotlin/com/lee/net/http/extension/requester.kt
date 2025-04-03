package com.lee.net.http.extension

import com.lee.net.http.HttpRequester
import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.util.reflect.*

/**
 * Perform an HTTP request and get a result
 */
suspend inline fun <reified T> HttpRequester.perform(noinline block: suspend (HttpClient) -> HttpResponse): T {
    return perform(typeInfo<T>(), block)
}
