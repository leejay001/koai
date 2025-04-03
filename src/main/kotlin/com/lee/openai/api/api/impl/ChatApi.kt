package com.lee.openai.api.api.impl


import com.lee.openai.api.chat.ChatCompletion
import com.lee.openai.api.chat.ChatCompletionChunk
import com.lee.net.http.extension.streamEventsFrom
import com.lee.net.http.HttpRequester
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.streamRequestOf
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import com.lee.net.http.extension.requestOptions
import com.lee.openai.api.api.Chat
import com.lee.openai.api.chat.ChatCompletionRequest
import com.lee.openai.api.core.RequestOptions
import kotlinx.coroutines.flow.flow

internal class ChatApi(private val requester: HttpRequester) : Chat {
    override suspend fun chatCompletion(
        request: ChatCompletionRequest,
        requestOptions: RequestOptions?
    ): ChatCompletion {
        return requester.perform {
            it.post {
                url(path = ApiPath.ChatCompletions)
                setBody(request)
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }.body()
        }
    }

    override fun chatCompletions(
        request: ChatCompletionRequest,
        requestOptions: RequestOptions?
    ): Flow<ChatCompletionChunk> {
        val builder = HttpRequestBuilder().apply {
            method = HttpMethod.Post
            url(path = ApiPath.ChatCompletions)
            setBody(streamRequestOf(request))
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
            requestOptions(requestOptions)
        }
        return flow {
            requester.perform(builder) { response -> streamEventsFrom(response) }
        }
    }
}
