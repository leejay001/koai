package com.lee.openai.api.api.impl

import com.lee.net.http.extension.beta
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.api.Messages
import com.lee.openai.api.core.PaginatedList
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.core.SortOrder
import com.lee.openai.api.file.FileId
import com.lee.openai.api.message.Message
import com.lee.openai.api.message.MessageFile
import com.lee.openai.api.message.MessageId
import com.lee.openai.api.message.MessageRequest
import com.lee.openai.api.thread.ThreadId
import com.lee.openai.exception.*

@OptIn(BetaOpenAI::class)
internal class MessagesApi(val requester: HttpRequester) : Messages {

    @OptIn(BetaOpenAI::class)
    override suspend fun message(
        threadId: ThreadId,
        request: MessageRequest,
        requestOptions: RequestOptions?
    ): Message {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/${threadId.id}/messages")
                setBody(request)
                contentType(ContentType.Application.Json)
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun message(threadId: ThreadId, messageId: MessageId, requestOptions: RequestOptions?): Message {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/messages/${messageId.id}")
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun message(
        threadId: ThreadId,
        messageId: MessageId,
        metadata: Map<String, String>?,
        requestOptions: RequestOptions?
    ): Message {
        return requester.perform {
            it.post {
                url(path = "${ApiPath.Threads}/${threadId.id}/messages/${messageId.id}")
                metadata?.let { meta ->
                    setBody(mapOf("metadata" to meta))
                    contentType(ContentType.Application.Json)
                }
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @BetaOpenAI
    override suspend fun messages(
        threadId: ThreadId,
        limit: Int?,
        order: SortOrder?,
        after: MessageId?,
        before: MessageId?,
        requestOptions: RequestOptions?
    ): PaginatedList<Message> {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/messages") {
                    limit?.let { value -> parameter("limit", value) }
                    order?.let { value -> parameter("order", value.order) }
                    before?.let { value -> parameter("before", value.id) }
                    after?.let { value -> parameter("after", value.id) }
                }
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun messageFile(
        threadId: ThreadId,
        messageId: MessageId,
        fileId: FileId,
        requestOptions: RequestOptions?
    ): MessageFile {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/messages/${messageId.id}/files/${fileId.id}")
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun messageFiles(
        threadId: ThreadId,
        messageId: MessageId,
        limit: Int?,
        order: SortOrder?,
        after: FileId?,
        before: FileId?,
        requestOptions: RequestOptions?
    ): PaginatedList<MessageFile> {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Threads}/${threadId.id}/messages/${messageId.id}/files") {
                    limit?.let { value -> parameter("limit", value) }
                    order?.let { value -> parameter("order", value.order) }
                    before?.let { value -> parameter("before", value.id) }
                    after?.let { value -> parameter("after", value.id) }
                }
                beta("assistants", 1)
                requestOptions(requestOptions)
            }.body()
        }
    }
}
