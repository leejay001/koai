package com.lee.openai.api.chat

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * A list of tools the model may call. Use this to provide a list of functions the model may generate JSON inputs for.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public data class Tool(

    /**
     * The type of the tool.
     * see [ToolType]
     */
    @JsonProperty("type") val type: String,

    /**
     * A description of what the function does, used by the model to choose when and how to call the function.
     */
    @JsonProperty("function") val function: FunctionTool,

    /**
     * openai true only
     */
    @JsonProperty("strict") val strict: Boolean? = null
) {

    public companion object {

        /**
         * Creates a 'function' tool.
         *
         * @param name The name of the function to be called. Must be a-z, A-Z, 0-9, or contain underscores and dashes,
         * with a maximum length of 64.
         * @param parameters The parameters the function accepts, described as a JSON Schema object.
         */
        public fun function(name: String, strict: Boolean = true,description: String? = null, parameters: JsonNode): Tool =
            Tool(
                type = ToolType.Function.value,
                function = FunctionTool(name = name, description = description, parameters = parameters),
                strict = true
            )
    }
}

/**
 * A description of what the function does, used by the model to choose when and how to call the function.
 */
public data class FunctionTool(
    /**
     * The name of the function to be called. Must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum
     * length of 64.
     */
    @JsonProperty("name") val name: String,

    /**
     * The parameters the functions accept, described as a JSON Schema object.
     * See the [guide](https://platform.openai.com/docs/guides/text-generation/function-calling) for examples,
     * and the [JSON Schema reference](https://json-schema.org/understanding-json-schema) for documentation about
     * the format.
     *
     * Omitting `parameters` defines a function with an empty parameter list.
     */
    @JsonProperty("parameters") val parameters: JsonNode? = null,

    /**
     * A description of what the function does, used by the model to choose when and how to call the function.
     */
    @JsonProperty("description") public val description: String? = null
)
