package com.lee.openai.api.vectorstore

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.batch.BatchId
import com.lee.openai.api.core.Status
import com.lee.openai.api.file.FileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A batch of files attached to a vector store.
 */
@BetaOpenAI
@Serializable
public data class FilesBatch(

    /**
     * The identifier, which can be referenced in API endpoints.
     */
    @SerialName("id") public val id: BatchId,

    /**
     * The Unix timestamp (in seconds) for when the vector store files batch was created.
     */
    @SerialName("created_at") public val createdAt: Long,

    /**
     * The ID of the vector store that the File is attached to.
     * [FileId]
     */
    @SerialName("vector_store_id") public val vectorStoreId: String,

    /**
     * The status of the vector store files batch, which can be either [Status.InProgress], [Status.Completed],
     * [Status.Cancelled] or [Status.Failed].
     * [Status]
     */
    @SerialName("status") public val status: String,

    /**
     * Files that are currently being processed.
     */
    @SerialName("file_counts") public val fileCounts: FileCounts,
)
