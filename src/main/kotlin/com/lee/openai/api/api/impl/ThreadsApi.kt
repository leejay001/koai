package com.lee.openai.api.api.impl

import com.lee.net.http.extension.beta
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.api.Threads
import com.lee.openai.api.core.DeleteResponse
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.thread.Thread
import com.lee.openai.api.thread.ThreadId
import com.lee.openai.api.thread.ThreadRequest
import com.lee.openai.exception.*

@OptIn(BetaOpenAI::class)
internal class ThreadsApi(val requester: HttpRequester) : Threads {

    @BetaOpenAI
    override suspend fun thread(request: ThreadRequest?, requestOptions: RequestOptions?): com.lee.openai.api.thread.Thread {
        return requester.perform {
            it.post {
                url(path = ApiPath.Threads)
                request?.let { req ->
                    setBody(req)
                    contentType(ContentType.Application.Json)
                }
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun thread(id: ThreadId, requestOptions: RequestOptions?): com.lee.openai.api.thread.Thread? {
        try {
            return requester.perform<HttpResponse> {
                it.get {
                    url(path = "${ApiPath.Threads}/${id.id}")
                    beta("assistants", 1)
                    requestOptions(requestOptions)
                }
            }.body()
        } catch (e: OpenAIAPIException) {
            if (e.statusCode == HttpStatusCode.NotFound.value) return null
            throw e
        }
    }

    @BetaOpenAI
    override suspend fun thread(id: ThreadId, metadata: Map<String, String>, requestOptions: RequestOptions?): Thread {
        val request = buildJsonObject {
            putJsonObject("metadata") {
                metadata.forEach { (key, value) ->
                    put(key, value)
                }
            }
        }
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/${id.id}")
                setBody(request)
                contentType(ContentType.Application.Json)
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun delete(id: ThreadId, requestOptions: RequestOptions?): Boolean {
        val response = requester.perform<HttpResponse> {
            it.delete {
                url(path = "${ApiPath.Threads}/${id.id}")
                beta("assistants", 1)
                requestOptions(requestOptions)
            }
        }
        return when (response.status) {
            HttpStatusCode.NotFound -> false
            else -> response.body<DeleteResponse>().deleted
        }
    }
}
