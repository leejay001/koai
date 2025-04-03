package com.lee.openai.api.api.impl

import com.lee.net.http.HttpRequester
import com.lee.net.http.extension.beta
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.api.Batch
import com.lee.openai.api.batch.BatchRequest
import com.lee.openai.api.core.RequestOptions
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.lee.openai.api.batch.Batch as BatchObject
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.openai.api.batch.BatchId
import com.lee.openai.api.core.PaginatedList
import com.lee.openai.exception.*

/**
 * Implementation of [Batch].
 */
internal class BatchApi(val requester: HttpRequester) : Batch {

    @OptIn(BetaOpenAI::class)
    override suspend fun batch(
        request: BatchRequest,
        requestOptions: RequestOptions?
    ): BatchObject {
        return requester.perform {
            it.post {
                url(path = ApiPath.Batches)
                setBody(request)
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun batch(id: BatchId, requestOptions: RequestOptions?): BatchObject? {
        try {
            return requester.perform<HttpResponse> {
                it.get {
                    url(path = "${ApiPath.Batches}/${id.id}")
                    requestOptions(requestOptions)
                }
            }.body()
        } catch (e: OpenAIAPIException) {
            if (e.statusCode == HttpStatusCode.NotFound.value) return null
            throw e
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun cancel(id: BatchId, requestOptions: RequestOptions?): BatchObject? {
        val response = requester.perform<HttpResponse> {
            it.post {
                url(path = "${ApiPath.Batches}/${id.id}/cancel")
                requestOptions(requestOptions)
            }
        }
        return if (response.status == HttpStatusCode.NotFound) null else response.body()
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun batches(
        after: BatchId?,
        limit: Int?,
        requestOptions: RequestOptions?
    ): PaginatedList<BatchObject> {
        return requester.perform {
            it.get {
                url {
                    path(ApiPath.Batches)
                    limit?.let { parameter("limit", it) }
                    after?.let { parameter("after", it.id) }
                }
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }
}
