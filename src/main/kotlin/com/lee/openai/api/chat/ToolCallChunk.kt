package com.lee.openai.api.chat

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details of the tool call chunk.
 */
public data class ToolCallChunk(
    /** Tool call index. Required in the case of chat stream variant **/
    @JsonProperty("index") val index: Int,
    /** The type of the tool call. **/
    @JsonProperty("type") val type: String? = null,
    /** The ID of the tool call. see [ToolId] **/
    @JsonProperty("id") val id: String? = null,
    /** The function that the model called. **/
    @JsonProperty("function") val function: FunctionCall? = null,
)
