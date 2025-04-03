package com.lee.openai.api.api.impl

import com.lee.net.http.extension.beta
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.api.VectorStores
import com.lee.openai.api.batch.BatchId
import com.lee.openai.api.core.*
import com.lee.openai.api.file.FileId
import com.lee.openai.api.vectorstore.*
import com.lee.openai.exception.*

internal class VectorStoresApi(private val requester: HttpRequester) : VectorStores {

    @OptIn(BetaOpenAI::class)
    override suspend fun createVectorStore(
        request: VectorStoreRequest?,
        requestOptions: RequestOptions?
    ): VectorStore {
        return requester.perform {
            it.post {
                url(path = ApiPath.VectorStores)
                request?.let { req ->
                    setBody(req)
                    contentType(ContentType.Application.Json)
                }
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun vectorStores(
        limit: Int?,
        order: SortOrder?,
        after: VectorStoreId?,
        before: VectorStoreId?,
        requestOptions: RequestOptions?
    ): PaginatedList<VectorStore> {
        return requester.perform {
            it.get {
                url {
                    path(ApiPath.VectorStores)
                    limit?.let { parameter("limit", it) }
                    order?.let { parameter("order", it.order) }
                    after?.let { parameter("after", it.id) }
                    before?.let { parameter("before", it.id) }
                }
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun vectorStore(id: VectorStoreId, requestOptions: RequestOptions?): VectorStore? {
        try {
            return requester.perform<HttpResponse> {
                it.get {
                    url(path = "${ApiPath.VectorStores}/${id.id}")
                    beta("assistants", 2)
                    requestOptions(requestOptions)
                }
            }.body()
        } catch (e: OpenAIAPIException) {
            if (e.statusCode == HttpStatusCode.NotFound.value) return null
            throw e
        }
    }

    @BetaOpenAI
    override suspend fun updateVectorStore(
        id: VectorStoreId,
        request: VectorStoreRequest,
        requestOptions: RequestOptions?
    ): VectorStore {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.VectorStores}/${id.id}")
                setBody(request)
                contentType(ContentType.Application.Json)
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun delete(id: VectorStoreId, requestOptions: RequestOptions?): Boolean {
        val response = requester.perform<HttpResponse> {
            it.delete {
                url(path = "${ApiPath.VectorStores}/${id.id}")
                beta("assistants", 2)
                requestOptions(requestOptions)
            }
        }
        return when (response.status) {
            HttpStatusCode.NotFound -> false
            else -> response.body<DeleteResponse>().deleted
        }
    }

    @BetaOpenAI
    override suspend fun createVectorStoreFile(
        id: VectorStoreId,
        request: VectorStoreFileRequest,
        requestOptions: RequestOptions?
    ): VectorStoreFile {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.VectorStores}/${id.id}/files")
                setBody(request)
                contentType(ContentType.Application.Json)
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun vectorStoreFiles(
        id: VectorStoreId,
        limit: Int?,
        order: SortOrder?,
        after: VectorStoreId?,
        before: VectorStoreId?,
        filter: Status?,
        requestOptions: RequestOptions?
    ): PaginatedList<VectorStoreFile> {
        return requester.perform {
            it.get {
                url {
                    path("${ApiPath.VectorStores}/${id.id}/files")
                    limit?.let { parameter("limit", it) }
                    order?.let { parameter("order", it.order) }
                    after?.let { parameter("after", it.id) }
                    before?.let { parameter("before", it.id) }
                    filter?.let { parameter("filter", it.value) }
                }
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun delete(id: VectorStoreId, fileId: FileId, requestOptions: RequestOptions?): Boolean {
        val response = requester.perform<HttpResponse> {
            it.delete {
                url(path = "${ApiPath.VectorStores}/${id.id}/files/${fileId.id}")
                beta("assistants", 2)
                requestOptions(requestOptions)
            }
        }
        return when (response.status) {
            HttpStatusCode.NotFound -> false
            else -> response.body<DeleteResponse>().deleted
        }
    }

    @BetaOpenAI
    override suspend fun createVectorStoreFilesBatch(
        id: VectorStoreId,
        request: FileBatchRequest,
        requestOptions: RequestOptions?
    ): FilesBatch {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.VectorStores}/${id.id}/file_batches")
                setBody(request)
                contentType(ContentType.Application.Json)
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun vectorStoreFileBatch(
        vectorStoreId: VectorStoreId,
        batchId: BatchId,
        requestOptions: RequestOptions?
    ): FilesBatch? {
        try {
            return requester.perform<HttpResponse> {
                it.get {
                    url(path = "${ApiPath.VectorStores}/${vectorStoreId.id}/file_batches/${batchId.id}")
                    beta("assistants", 2)
                    requestOptions(requestOptions)
                }
            }.body()
        } catch (e: OpenAIAPIException) {
            if (e.statusCode == HttpStatusCode.NotFound.value) return null
            throw e
        }
    }

    @BetaOpenAI
    override suspend fun cancel(
        vectorStoreId: VectorStoreId,
        batchId: BatchId,
        requestOptions: RequestOptions?
    ): FilesBatch? {
        val response = requester.perform<HttpResponse> {
            it.post {
                url(path = "${ApiPath.VectorStores}/${vectorStoreId.id}/file_batches/${batchId.id}/cancel")
                beta("assistants", 2)
                requestOptions(requestOptions)
            }
        }
        return if (response.status == HttpStatusCode.NotFound) null else response.body()
    }

    @BetaOpenAI
    override suspend fun vectorStoreFilesBatches(
        vectorStoreId: VectorStoreId,
        batchId: BatchId,
        limit: Int?,
        order: SortOrder?,
        after: VectorStoreId?,
        before: VectorStoreId?,
        filter: Status?,
        requestOptions: RequestOptions?
    ): PaginatedList<VectorStoreFile> {
        return requester.perform {
            it.get {
                url {
                    path("${ApiPath.VectorStores}/${vectorStoreId.id}/file_batches/${batchId.id}/files")
                    limit?.let { parameter("limit", it) }
                    order?.let { parameter("order", it.order) }
                    after?.let { parameter("after", it.id) }
                    before?.let { parameter("before", it.id) }
                    filter?.let { parameter("filter", it.value) }
                }
                beta("assistants", 2)
                requestOptions(requestOptions)
            }.body()
        }
    }
}
