package com.lee.openai.api.chat

//import com.lee.openai.api.chat.internal.ToolChoiceSerializer
//import kotlinx.serialization.SerialName
//import kotlinx.serialization.Serializable
//import kotlin.jvm.JvmInline
//
///**
// * Controls which (if any) function is called by the model.
// */
//@Serializable(with = ToolChoiceSerializer::class)
//public sealed interface ToolChoice {
//
//    /**
//     * Represents a function call mode.
//     * - `"none"` means the model will not call a function and instead generates a message.
//     * - `"auto"` means the model can pick between generating a message or calling a function.
//     */
//    @JvmInline
//    @Serializable
//    public value class Mode(public val value: String) : ToolChoice
//
//    /**
//     * Specifies a tool the model should use.
//     */
//    @Serializable
//    public data class Named(
//        @SerialName("type") public val type: ToolType? = null,
//        @SerialName("function") public val function: FunctionToolChoice? = null,
//    ) : ToolChoice
//
//    public companion object {
//        /** Represents the `auto` mode. */
//        public val Auto: ToolChoice = Mode("auto")
//
//        /** Represents the `none` mode. */
//        public val None: ToolChoice = Mode("none")
//
//        /** Specifies a function for the model to call **/
//        public fun function(name: String): ToolChoice =
//            Named(type = ToolType.Function, function = FunctionToolChoice(name = name))
//    }
//}
//
///**
// * Represents the function to call by the model.
// */
//@Serializable
//public data class FunctionToolChoice(val name: String)


import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.ser.std.StdSerializer


/**
 * https://platform.openai.com/docs/guides/function-calling
 * Configuring function calling behavior using the tool_choice parameter
 * By default, the model is configured to automatically select which functions to call, as determined by the tool_choice: "auto" setting.
 *
 * We offer three ways to customize the default behavior:
 *
 * To force the model to always call one or more functions, you can set tool_choice: "required". The model will then always select one or more function(s) to call. This is useful for example if you want the model to pick between multiple actions to perform next.
 * To force the model to call a specific function, you can set tool_choice: {"type": "function", "function": {"name": "my_function"}}.
 * To disable function calling and force the model to only generate a user-facing message, you can either provide no tools, or set tool_choice: "none".
 */

@JsonSerialize(using = ToolChoiceSerializer::class)
@JsonDeserialize(using = ToolChoiceDeserializer::class)
sealed interface ToolChoice {
    data class StringMode(val value: String) : ToolChoice
    data class Named(val type: String, val function: FunctionToolChoice) : ToolChoice

    companion object {
        val Auto: ToolChoice = StringMode("auto")
        val None: ToolChoice = StringMode("none")
        val Required: ToolChoice = StringMode("required")

        fun function(name: String): ToolChoice = Named("function", FunctionToolChoice(name))
    }
}

data class FunctionToolChoice(val name: String)

class ToolChoiceSerializer : StdSerializer<ToolChoice>(ToolChoice::class.java) {
    override fun serialize(value: ToolChoice, gen: JsonGenerator, provider: SerializerProvider) {
        when (value) {
            is ToolChoice.StringMode -> gen.writeString(value.value)
            is ToolChoice.Named -> {
                gen.writeStartObject()
                gen.writeStringField("type", value.type)
                gen.writeObjectFieldStart("function")
                gen.writeStringField("name", value.function.name)
                gen.writeEndObject()
                gen.writeEndObject()
            }
        }
    }
}

class ToolChoiceDeserializer : StdDeserializer<ToolChoice>(ToolChoice::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ToolChoice {
        val node = p.codec.readTree<JsonNode>(p)
        return when {
            node.isTextual -> ToolChoice.StringMode(node.asText())
            node.isObject -> {
                val objectNode = node as ObjectNode
                val type = objectNode.get("type").asText()
                val functionNode = objectNode.get("function")
                val functionName = functionNode.get("name").asText()
                ToolChoice.Named(type, FunctionToolChoice(functionName))
            }
            else -> throw JsonMappingException(p, "Invalid ToolChoice format")
        }
    }
}
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
//@JsonSubTypes(
//    JsonSubTypes.Type(value = ToolChoice.Mode::class, name = "mode"),
//    JsonSubTypes.Type(value = ToolChoice.Named::class, name = "named")
//)
//sealed interface ToolChoice {
//
//    @JvmInline
//    @JsonSerialize(using = ModeSerializer::class)
//    @JsonDeserialize(using = ModeDeserializer::class)
//    value class Mode(val value: String) : ToolChoice
//
//    @JsonDeserialize(using = NamedDeserializerToolChoice::class)
//    @JsonSerialize(using = NamedSerializerToolChoice::class)
//    data class Named(
//        val type: ToolType? = null,
//        val function: FunctionToolChoice? = null
//    ) : ToolChoice
//
//    companion object {
//        val Auto: ToolChoice = Mode("auto")
//        val None: ToolChoice = Mode("none")
//        val REQUIRED: ToolChoice = Mode("required")
//
//        fun function(name: String): ToolChoice =
//            Named(type = ToolType.Function, function = FunctionToolChoice(name = name))
//    }
//}
//
//data class FunctionToolChoice(val name: String)
//
//
//
//
//class ModeSerializer : JsonSerializer<ToolChoice.Mode>() {
//    override fun serialize(value: ToolChoice.Mode, gen: JsonGenerator, serializers: SerializerProvider) {
//        gen.writeString(value.value)
//    }
//}
//
//class ModeDeserializer : JsonDeserializer<ToolChoice.Mode>() {
//    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ToolChoice.Mode {
//        return ToolChoice.Mode(p.valueAsString)
//    }
//}
//
//class NamedSerializerToolChoice : JsonSerializer<ToolChoice.Named>() {
//    override fun serialize(value: ToolChoice.Named, gen: JsonGenerator, serializers: SerializerProvider) {
//        gen.writeStartObject()
//        gen.writeObjectFieldStart("named")
//        gen.writeObjectField("type", value.type)
//        gen.writeObjectField("function", value.function)
//        gen.writeEndObject()
//        gen.writeEndObject()
//    }
//}
//
//class NamedDeserializerToolChoice : JsonDeserializer<ToolChoice.Named>() {
//    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ToolChoice.Named {
//        val node = p.codec.readTree<ObjectNode>(p)
//        val type = p.codec.treeToValue(node.get("type"), ToolType::class.java)
//        val function = p.codec.treeToValue(node.get("function"), FunctionToolChoice::class.java)
//        return ToolChoice.Named(type, function)
//    }
//}

