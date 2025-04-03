package com.lee.openai.api.chat

import com.azure.core.annotation.Generated
import com.azure.core.annotation.Immutable
import com.fasterxml.jackson.annotation.*
import com.lee.openai.anotations.OpenAIDsl
import kotlinx.serialization.SerialName

/**
 * Details of the tool call.
 */

@JsonTypeName("ToolCall")
@JsonSubTypes(JsonSubTypes.Type(name = "function", value = Function::class))
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = ToolCall::class)
public open class ToolCall {

    /*
     * The ID of the tool call.
     */
    @JsonProperty(value = "id")
    protected open var id: String? = null

    @JsonProperty("type")
    protected open var type: String? = null

}


@JsonTypeName("function")
public class Function(
    /** The ID of the tool call. see [ToolId] **/
    @JsonProperty("id") override var id: String?,
    /** The function that the model called. **/
    @JsonProperty("function") val function: FunctionCall,
) : ToolCall() {
    init {
        this.type = "function"
    }
}
