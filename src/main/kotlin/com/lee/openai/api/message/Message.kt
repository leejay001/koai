package com.lee.openai.api.message

import com.lee.openai.api.assistant.AssistantId
import com.lee.openai.api.core.Role
import com.lee.openai.api.file.FileId
import com.lee.openai.api.run.RunId
import com.lee.openai.api.thread.ThreadId
import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a message within a thread.
 */
@Serializable
@BetaOpenAI
public data class Message(
    /**
     * The identifier, which can be referenced in API endpoints.
     * see [MessageId]
     */
    @SerialName("id") val id: String,

    /**
     * The Unix timestamp (in seconds) for when the message was created.
     */
    @SerialName("created_at") val createdAt: Int,

    /**
     * The thread ID that this message belongs to.
     * [ThreadId]
     */
    @SerialName("thread_id") val threadId: String,

    /**
     * The entity that produced the message. One of user or assistant.
     * see [Role]
     */
    @SerialName("role") val role: String,

    /**
     * The content of the message in an array of text and/or images.
     */
    @SerialName("content") val content: List<MessageContent>,

    /**
     * If applicable, the ID of the assistant that authored this message.
     * Can be null.
     *
     * see [AssistantId]
     *
     */
    @SerialName("assistant_id") val assistantId: String? = null,

    /**
     * If applicable, the ID of the run associated with the authoring of this message.
     * Can be null.
     * [RunId]
     */
    @SerialName("run_id") val runId: String? = null,

    /**
     * A list of file IDs that the assistant should use. Useful for tools like retrieval
     * and code_interpreter that can access files. A maximum of 10 files can be attached to a message.
     * see [FileId]
     */
    @SerialName("file_ids") val fileIds: List<String>,

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing
     * additional information about the object in a structured format. Keys can be a maximum of 64 characters
     * long, and values can be a maximum of 512 characters long.
     */
    @SerialName("metadata") val metadata: Map<String, String>
)
