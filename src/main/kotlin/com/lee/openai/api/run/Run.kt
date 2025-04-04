package com.lee.openai.api.run

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.assistant.AssistantId
import com.lee.openai.api.assistant.AssistantTool
import com.lee.openai.api.core.Status
import com.lee.openai.api.model.ModelId
import com.lee.openai.api.thread.ThreadId
import com.lee.openai.api.core.LastError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an execution run on a thread.
 */
@Serializable
@BetaOpenAI
public data class Run(
    /**
     * The identifier, which can be referenced in API endpoints.
     * [RunId]
     */
    @SerialName("id") val id: String,

    /**
     * The Unix timestamp (in seconds) for when the run was created.
     */
    @SerialName("created_at") val createdAt: Int,

    /**
     * The ID of the thread that was executed on as a part of this run.
     * [ThreadId]
     */
    @SerialName("thread_id") val threadId: String,

    /**
     * The ID of the assistant used for execution of this run.
     * [AssistantId]
     */
    @SerialName("assistant_id") val assistantId: String,

    /**
     * The status of the run, which can be either [Status.Queued], [Status.InProgress], [Status.RequiresAction],
     * [Status.Cancelling], [Status.Cancelled], [Status.Failed], [Status.Completed], or [Status.Expired].
     * see [Status]
     */
    @SerialName("status") val status: String,
    /**
     * Details on the action required to continue the run. Will be `null` if no action is required.
     */
    @SerialName("required_action") val requiredAction: RequiredAction? = null,

    /**
     * The last error associated with this run. Will be null if there are no errors.
     */
    @SerialName("last_error") val lastError: LastError? = null,

    /**
     * The Unix timestamp (in seconds) for when the run will expire.
     */
    @SerialName("expires_at") val expiresAt: Int? = null,

    /**
     * The Unix timestamp (in seconds) for when the run was started.
     */
    @SerialName("started_at") val startedAt: Int? = null,

    /**
     * The Unix timestamp (in seconds) for when the run was cancelled.
     */
    @SerialName("cancelled_at") val cancelledAt: Int? = null,

    /**
     * The Unix timestamp (in seconds) for when the run failed.
     */
    @SerialName("failed_at") val failedAt: Int? = null,

    /**
     * The Unix timestamp (in seconds) for when the run was completed.
     */
    @SerialName("completed_at") val completedAt: Int? = null,

    /**
     * The model that the assistant used for this run.
     */
    @SerialName("model") val model: ModelId,

    /**
     * The instructions that the assistant used for this run.
     */
    @SerialName("instructions") val instructions: String? = null,

    /**
     * The list of tools that the assistant used for this run.
     */
    @SerialName("tools") val tools: List<AssistantTool>? = null,

    /**
     * The list of File IDs the assistant used for this run.
     */
    @SerialName("file_ids") val fileIds: List<String>? = null,

    /**
     * Set of 16 key-value pairs that can be attached to an object.
     * This can be useful for storing additional information about the object in a structured format.
     * Keys can be a maximum of 64 characters long, and values can be a maximum of 512 characters long.
     */
    @SerialName("metadata") val metadata: Map<String, String>? = null,
)
