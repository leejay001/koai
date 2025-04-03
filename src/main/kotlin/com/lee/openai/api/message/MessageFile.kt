package com.lee.openai.api.message

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.file.FileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * File attached to a message.
 */
@BetaOpenAI
@Serializable
public data class MessageFile(
    /**
     * The identifier, which can be referenced in API endpoints.
     * see [FileId]
     */
    @SerialName("id") val id: String,
    /**
     * The Unix timestamp (in seconds) for when the message file was created.
     */
    @SerialName("created_at") val createdAt: Int? = null,
    /**
     * The ID of the message that the File is attached to.
     * see [MessageId]
     */
    @SerialName("message_id") val messageId: String? = null,
)
