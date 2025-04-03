package com.lee.openai.api.assistant


import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.file.FileId
import kotlinx.serialization.SerialName

/**
 * File attached to an assistant.
 */
@BetaOpenAI
public data class AssistantFile(
    /**
     * The identifier, which can be referenced in API endpoints.
     *
     * see [FileId]
     */
    @SerialName("id") public val id: String,

    /**
     * The Unix timestamp (in seconds) for when the assistant file was created.
     */
    @SerialName("created_at") public val createdAt: Int,

    /**
     * The assistant ID that the file is attached to.
     * see [AssistantId]
     */
    @SerialName("assistant_id") public val assistantId: String
)
