package com.lee.openai.api.run

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.assistant.AssistantId
import com.lee.openai.api.assistant.AssistantTool
import com.lee.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Create a run request.
 */
@BetaOpenAI
@Serializable
public data class RunRequest(
    /**
     * The ID of the assistant to use to execute this run.
     * [AssistantId]
     */
    @SerialName("assistant_id") val assistantId: String,

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
     * Appends additional instructions at the end of the instructions for the run.
     * This is useful for modifying the behavior on a per-run basis without overriding other instructions.
     */
    @SerialName("additional_instructions") val additionalInstructions: String? = null,

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
 * Create a run request.
 */
@BetaOpenAI
public fun runRequest(block: RunRequestBuilder.() -> Unit): RunRequest = RunRequestBuilder().apply(block).build()

/**
 * Builder for [RunRequest].
 */
@BetaOpenAI
public class RunRequestBuilder {

    /**
     * The ID of the assistant to use to execute this run.
     */
    public var assistantId: AssistantId? = null

    /**
     * The ID of the Model to be used to execute this run.
     * If a value is provided here, it will override the model associated with the assistant.
     * If not, the model associated with the assistant will be used.
     */
    public var model: ModelId? = null

    /**
     * Override the default system message of the assistant.
     * This is useful for modifying the behavior on a per-run basis.
     */
    public var instructions: String? = null

    /**
     * Appends additional instructions at the end of the instructions for the run.
     * This is useful for modifying the behavior on a per-run basis without overriding other instructions.
     */
    public var additionalInstructions: String? = null

    /**
     * Override the tools the assistant can use for this run.
     * This is useful for modifying the behavior on a per-run basis.
     */
    public var tools: List<AssistantTool>? = null

    /**
     * Set of 16 key-value pairs that can be attached to an object.
     * This can be useful for storing additional information about the object in a structured format.
     * Keys can be a maximum of 64 characters long, and values can be a maximum of 512 characters long.
     */
    public var metadata: Map<String, String>? = null

    /**
     * Build a [RunRequest] instance.
     */
    public fun build(): RunRequest = RunRequest(
        assistantId = requireNotNull(assistantId?.id) { "assistantId is required" },
        model = model?.id,
        instructions = instructions,
        additionalInstructions = additionalInstructions,
        tools = tools,
        metadata = metadata,
    )
}
