package com.lee.openai.api.run

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.chat.ToolCall
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details on the tool outputs needed for this run to continue.
 */
@BetaOpenAI
@Serializable
public data class ToolOutputs(
    /**
     * A list of the relevant tool calls
     */
    @SerialName("tool_calls") val toolCalls: List<ToolCall>
)
