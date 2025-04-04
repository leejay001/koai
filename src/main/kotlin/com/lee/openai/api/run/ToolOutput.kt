package com.lee.openai.api.run

import com.lee.openai.api.chat.ToolId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.anotations.OpenAIDsl

/**
 * Represents a tool output.
 */
@BetaOpenAI
@Serializable
public data class ToolOutput(
    /**
     * The ID of the tool call in the required_action object within the run object the output is being submitted for.
     * [ToolId]
     */
    @SerialName("tool_call_id") val toolCallId: String? = null,
    /**
     * The output of the tool call to be submitted to continue the run.
     */
    @SerialName("output") val output: String? = null,
)

/**
 * Creates a [ToolOutput] instance using the provided builder block.
 */
@BetaOpenAI
public fun toolOutput(block: ToolOutputBuilder.() -> Unit): ToolOutput = ToolOutputBuilder().apply(block).build()

/**
 * A tool output builder.
 */
@BetaOpenAI
@OpenAIDsl
public class ToolOutputBuilder {
    /**
     * The ID of the tool call in the required_action object within the run object the output is being submitted for.
     */
    public var toolCallId: ToolId? = null

    /**
     * The output of the tool call to be submitted to continue the run.
     */
    public var output: String? = null

    /**
     * Builds and returns a [ToolOutput] instance.
     */
    public fun build(): ToolOutput = ToolOutput(
        toolCallId = toolCallId?.id,
        output = output
    )
}
