package com.lee.openai.api.api.impl

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.api.api.FineTuning
import com.lee.openai.api.core.PaginatedList
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.finetuning.FineTuningId
import com.lee.openai.api.finetuning.FineTuningJob
import com.lee.openai.api.finetuning.FineTuningJobEvent
import com.lee.openai.api.finetuning.FineTuningRequest
import com.lee.openai.exception.*


internal class FineTuningApi(private val requester: HttpRequester) : FineTuning {
    override suspend fun fineTuningJob(request: FineTuningRequest, requestOptions: RequestOptions?): FineTuningJob {
        return requester.perform {
            it.post {
                url(path = ApiPath.FineTuningJobs)
                setBody(request)
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }
        }
    }

    override suspend fun fineTuningJobs(
        after: String?,
        limit: Int?,
        requestOptions: RequestOptions?
    ): PaginatedList<FineTuningJob> {
        return requester.perform {
            it.get {
                url(path = ApiPath.FineTuningJobs) {
                    after?.let { value -> parameter("after", value) }
                    limit?.let { value -> parameter("limit", value) }
                }
                requestOptions(requestOptions)
            }
        }
    }

    override suspend fun fineTuningJob(id: FineTuningId, requestOptions: RequestOptions?): FineTuningJob? {
        val response = requester.perform<HttpResponse> {
            it.get {
                url(path = "${ApiPath.FineTuningJobs}/${id.id}")
                requestOptions(requestOptions)
            }
        }
        return if (response.status == HttpStatusCode.NotFound) null else response.body()
    }

    override suspend fun cancel(id: FineTuningId, requestOptions: RequestOptions?): FineTuningJob? {
        val response = requester.perform<HttpResponse> {
            it.post {
                url(path = "${ApiPath.FineTuningJobs}/${id.id}/cancel")
                requestOptions(requestOptions)
            }
        }
        return if (response.status == HttpStatusCode.NotFound) null else response.body()
    }

    override suspend fun fineTuningEvents(
        id: FineTuningId,
        after: String?,
        limit: Int?,
        requestOptions: RequestOptions?
    ): PaginatedList<FineTuningJobEvent> {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.FineTuningJobs}/${id.id}/events") {
                    after?.let { value -> parameter("after", value) }
                    limit?.let { value -> parameter("limit", value) }
                }
                requestOptions(requestOptions)
            }
        }
    }
}
