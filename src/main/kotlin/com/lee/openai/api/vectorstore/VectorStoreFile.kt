package com.lee.openai.api.vectorstore

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.core.LastError
import com.lee.openai.api.core.Status
import com.lee.openai.api.file.FileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A list of files attached to a vector store.
 */
@BetaOpenAI
@Serializable
public data class VectorStoreFile(
    /**
     * The identifier, which can be referenced in API endpoints.
     * [FileId]
     */
    @SerialName("id") public val id: String,

    /**
     * Usage bytes.
     */
    @SerialName("usage_bytes") public val usageBytes: Long? = null,

    /**
     * The Unix timestamp (in seconds) for when the vector store file was created.
     */
    @SerialName("created_at") public val createdAt: Long,

    /**
     * The ID of the vector store that the File is attached to.
     * [VectorStoreId]
     */
    @SerialName("vector_store_id") public val vectorStoreId: String,

    /**
     * The status of the vector store file, which can be either in_progress, completed, cancelled, or failed.
     * The status completed indicates that the vector store file is ready for use.
     * [Status]
     */
    @SerialName("status") public val status: String,

    /**
     * The last error associated with this vector store file. Will be `null` if there are no errors.
     */
    @SerialName("last_error") public val lastError: LastError? = null,
)
