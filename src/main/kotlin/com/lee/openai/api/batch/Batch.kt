package com.lee.openai.api.batch

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.core.Endpoint
import com.lee.openai.api.core.PaginatedList
import com.lee.openai.api.core.Status
import com.lee.openai.api.file.FileId
import com.lee.openai.exception.OpenAIErrorDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a batch object.
 */
@BetaOpenAI
@Serializable
public data class Batch(
    /** Unique identifier for the batch.
     *  see [BatchId]
     * */
    @SerialName("id") public val id: String,

    /** The OpenAI API endpoint used by the batch. see [Endpoint] */
    @SerialName("endpoint") public val endpoint: String,

    /** Container for any errors occurred during batch processing. */
    @SerialName("errors") public val errors: PaginatedList<OpenAIErrorDetails>?,

    /** Identifier of the input file for the batch. see [FileId] */
    @SerialName("input_file_id") public val inputFileId: String? = null,

    /** Time frame within which the batch should be processed.  see [CompletionWindow] */
    @SerialName("completion_window") public val completionWindow: String? = null,

    /** Current processing status of the batch. see [Status] */
    @SerialName("status") public val status: String? = null,

    /** Identifier of the output file containing successfully executed requests.  see [FileId]*/
    @SerialName("output_file_id") public val outputFileId: String? = null,

    /** Identifier of the error file containing outputs of requests with errors. see [FileId] */
    @SerialName("error_file_id") public val errorFileId: String? = null,

    /** Unix timestamp for when the batch was created. */
    @SerialName("created_at") public val createdAt: Long? = null,

    /** Unix timestamp for when the batch processing started. */
    @SerialName("in_progress_at") public val inProgressAt: Long? = null,

    /** Unix timestamp for when the batch will expire. */
    @SerialName("expires_at") public val expiresAt: Long? = null,

    /** Unix timestamp for when the batch started finalizing. */
    @SerialName("finalizing_at") public val finalizingAt: Long? = null,

    /** Unix timestamp for when the batch was completed. */
    @SerialName("completed_at") public val completedAt: Long? = null,

    /** Unix timestamp for when the batch failed. */
    @SerialName("failed_at") public val failedAt: Long? = null,

    /** Unix timestamp for when the batch expired. */
    @SerialName("expired_at") public val expiredAt: Long? = null,

    /** Unix timestamp for when the batch started cancelling. */
    @SerialName("cancelling_at") public val cancellingAt: Long? = null,

    /** Unix timestamp for when the batch was cancelled. */
    @SerialName("cancelled_at") public val cancelledAt: Long? = null,

    /** Container for the counts of requests by their status. */
    @SerialName("request_counts") public val requestCounts: RequestCounts? = null,

    /** Metadata associated with the batch as key-value pairs. */
    @SerialName("metadata") public val metadata: Map<String, String>? = null
)
