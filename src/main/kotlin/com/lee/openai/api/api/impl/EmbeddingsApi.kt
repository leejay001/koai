package com.lee.openai.api.api.impl

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.api.api.Embeddings
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.embedding.EmbeddingRequest
import com.lee.openai.api.embedding.EmbeddingResponse

/**
 * Implementation of [Embeddings].
 */
internal class EmbeddingsApi(private val requester: HttpRequester) : Embeddings {

    override suspend fun embeddings(request: EmbeddingRequest, requestOptions: RequestOptions?): EmbeddingResponse {
        return requester.perform {
            it.post {
                url(path = ApiPath.Embeddings)
                setBody(request)
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }.body()
        }
    }
}
