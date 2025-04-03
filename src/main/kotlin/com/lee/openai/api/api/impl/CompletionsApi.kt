package com.lee.openai.api.api.impl

import com.lee.net.http.extension.streamEventsFrom
import com.lee.net.http.HttpRequester
import com.lee.net.http.extension.toStreamRequest
import com.lee.openai.api.api.Completions
import com.lee.openai.api.completion.CompletionRequest
import com.lee.openai.api.completion.TextCompletion
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import com.lee.net.http.extension.perform
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.flow


/**
 * Implementation of [Completions].
 */
internal class CompletionsApi(private val requester: HttpRequester) : Completions {

    override suspend fun completion(request: CompletionRequest): TextCompletion {
        return requester.perform {
            it.post {
                url(path = ApiPath.Completions)
                setBody(request)
                contentType(ContentType.Application.Json)
            }.body()
        }
    }

    override fun completions(request: CompletionRequest): Flow<TextCompletion> {
        val builder = HttpRequestBuilder().apply {
            method = HttpMethod.Post
            url(path = ApiPath.Completions)
            setBody(request.toStreamRequest())
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
        return flow {
            requester.perform(builder) { response -> streamEventsFrom(response) }
        }
    }
}
