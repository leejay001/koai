package com.lee.openai.api.run

import com.lee.openai.api.assistant.AssistantId
import com.lee.openai.api.assistant.AssistantTool
import com.lee.openai.api.model.ModelId
import com.lee.openai.api.thread.ThreadRequest
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.anotations.OpenAIDsl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Create a thread and run it in one request.
 */
@BetaOpenAI
@Serializable
public data class ThreadRunRequest(
    /**
     * The ID of the assistant to use to execute this run.
     * [AssistantId]
     */
    @SerialName("assistant_id") val assistantId: String,

    /**
     * Thread to be executed.
     */
    @SerialName("thread") val thread: ThreadRequest? = null,

    /**
     * The ID of the Model to be used to execute this run.
     * If a value is provided here, it will override the model associated with the assistant.
     * If not, the model associated with the assistant will be used.
     * [ModelId]
     */
    @SerialName("model") val model: String? = null,

    /**
     * Override the default system message of the assistant.
     * This is useful for modifying the behavior on a per-run basis.
     */
    @SerialName("instructions") val instructions: String? = null,

    /**
     * Override the tools the assistant can use for this run.
     * This is useful for modifying the behavior on a per-run basis.
     */
    @SerialName("tools") val tools: List<AssistantTool>? = null,

    /**
     * Set of 16 key-value pairs that can be attached to an object.
     * This can be useful for storing additional information about the object in a structured format.
     * Keys can be a maximum of 64 characters long, and values can be a maximum of 512 characters long.
     */
    @SerialName("metadata") val metadata: Map<String, String>? = null,
)

/**
 * Create a thread and run it in one request.

 */
@BetaOpenAI
public fun threadRunRequest(block: ThreadRunRequestBuilder.() -> Unit): ThreadRunRequest =
    ThreadRunRequestBuilder().apply(block).build()

/**
 * Create a thread and run it in one request.

 */
@BetaOpenAI
@OpenAIDsl
public class ThreadRunRequestBuilder {
    /**
     * The ID of the assistant to use to execute this run.
     */
    public val assistantId: AssistantId? = null

    /**
     * Thread to be executed.
     */
    public val thread: ThreadRequest? = null

    /**
     * The ID of the Model to be used to execute this run.
     * If a value is provided here, it will override the model associated with the assistant.
     * If not, the model associated with the assistant will be used.
     */
    public val model: ModelId? = null

    /**
     * Override the default system message of the assistant.
     * This is useful for modifying the behavior on a per-run basis.
     */
    public val instructions: String? = null

    /**
     * Override the tools the assistant can use for this run.
     * This is useful for modifying the behavior on a per-run basis.
     */
    public val tools: List<AssistantTool>? = null

    /**
     * Set of 16 key-value pairs that can be attached to an object.
     * This can be useful for storing additional information about the object in a structured format.
     * Keys can be a maximum of 64 characters long, and values can be a maximum of 512 characters long.
     */
    public val metadata: Map<String, String>? = null

    public fun build(): ThreadRunRequest = ThreadRunRequest(
        assistantId = requireNotNull(assistantId?.id) { "assistantId is required" },
        thread = thread,
        model = model?.id,
        instructions = instructions,
        tools = tools,
        metadata = metadata,
    )
}
