package com.lee.openai.api.api.impl

import com.lee.openai.api.run.RunStepId
import com.lee.net.http.extension.beta
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.api.Runs
import com.lee.openai.api.core.PaginatedList
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.core.SortOrder
import com.lee.openai.api.run.*
import com.lee.openai.api.thread.ThreadId
import com.lee.openai.exception.*

@OptIn(BetaOpenAI::class)
internal class RunsApi(val requester: HttpRequester) : Runs {
    @OptIn(BetaOpenAI::class)
    override suspend fun createRun(threadId: ThreadId, request: RunRequest, requestOptions: RequestOptions?): Run {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs")
                setBody(request)
                contentType(ContentType.Application.Json)
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun getRun(threadId: ThreadId, runId: RunId, requestOptions: RequestOptions?): Run {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs/${runId.id}")
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun updateRun(
        threadId: ThreadId,
        runId: RunId,
        metadata: Map<String, String>?,
        requestOptions: RequestOptions?
    ): Run {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs/${runId.id}")
                metadata?.let { meta ->
                    setBody(mapOf("metadata" to meta))
                    contentType(ContentType.Application.Json)
                }
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun runs(
        threadId: ThreadId,
        limit: Int?,
        order: SortOrder?,
        after: RunId?,
        before: RunId?,
        requestOptions: RequestOptions?
    ): PaginatedList<Run> {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs") {
                    limit?.let { parameter("limit", it) }
                    order?.let { parameter("order", it) }
                    after?.let { parameter("after", it.id) }
                    before?.let { parameter("before", it.id) }
                }
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun submitToolOutput(
        threadId: ThreadId,
        runId: RunId,
        output: List<ToolOutput>,
        requestOptions: RequestOptions?
    ): Run {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs/${runId.id}/submit_tool_outputs")
                setBody(mapOf("tool_outputs" to output))
                contentType(ContentType.Application.Json)
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun cancel(threadId: ThreadId, runId: RunId, requestOptions: RequestOptions?): Run {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs/${runId.id}/cancel")
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun createThreadRun(request: ThreadRunRequest, requestOptions: RequestOptions?): Run {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/runs")
                setBody(request)
                contentType(ContentType.Application.Json)
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun runStep(
        threadId: ThreadId,
        runId: RunId,
        stepId: RunStepId,
        requestOptions: RequestOptions?
    ): RunStep {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs/${runId.id}/steps/${stepId.id}")
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun runSteps(
        threadId: ThreadId,
        runId: RunId,
        limit: Int?,
        order: SortOrder?,
        after: RunStepId?,
        before: RunStepId?,
        requestOptions: RequestOptions?
    ): PaginatedList<RunStep> {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/runs/${runId.id}/steps") {
                    limit?.let { parameter("limit", it) }
                    order?.let { parameter("order", it.order) }
                    after?.let { parameter("after", it.id) }
                    before?.let { parameter("before", it.id) }
                }
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }
}
