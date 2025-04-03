package com.lee.openai.api.chat

//import com.lee.openai.api.chat.FunctionMode.Companion.Auto
//import com.lee.openai.api.chat.FunctionMode.Companion.None
//import com.lee.openai.api.chat.FunctionMode.Default
//import com.lee.openai.api.chat.FunctionMode.Named
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import kotlin.jvm.JvmInline

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * This interface determines how the model handles function calls.
 *
 * There are several modes of operation:
 * - [Default]: In this mode, the model does not invoke any function [None] or decides itself [Auto] on calling a function or responding directly to the end-user. This mode becomes default if any functions are specified.
 * - [Named]: In this mode, the model will call a specific function, denoted by the `name` attribute.
 */
//@Serializable(with = FunctionModeSerializer::class)
//public sealed interface FunctionMode {
//
//    /**
//     * Represents a function call mode.
//     * The value can be any string representing a specific function call mode.
//     */
//    @JvmInline
//    public value class Default(public val value: String) : FunctionMode
//
//    /**
//     * Represents a named function call mode.
//     * The name indicates a specific function that the model will call.
//     *
//     * @property name the name of the function to call.
//     */
//    @Serializable
//    public data class Named(public val name: String) : FunctionMode
//
//    /** Provides default function call modes. */
//    public companion object {
//        /** Represents the `auto` mode. */
//        public val Auto: FunctionMode = Default("auto")
//
//        /** Represents the `none` mode. */
//        public val None: FunctionMode = Default("none")
//    }
//}
//
//internal object FunctionModeSerializer : KSerializer<FunctionMode> {
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("FunctionCall")
//
//    override fun deserialize(decoder: Decoder): FunctionMode {
//        require(decoder is JsonDecoder) { "This decoder is not a JsonDecoder. Cannot deserialize `FunctionCall`" }
//        return when (val json = decoder.decodeJsonElement()) {
//            is JsonPrimitive -> Default(json.content)
//            is JsonObject -> json["name"]?.jsonPrimitive?.content?.let(FunctionMode::Named) ?: error("Missing 'name'")
//            else -> throw UnsupportedOperationException("Cannot deserialize FunctionMode. Unsupported JSON element.")
//        }
//    }
//
//    override fun serialize(encoder: Encoder, value: FunctionMode) {
//        require(encoder is JsonEncoder) { "This encoder is not a JsonEncoder. Cannot serialize `FunctionCall`" }
//        when (value) {
//            is Default -> encoder.encodeString(value.value)
//            is Named -> Named.serializer().serialize(encoder, value)
//        }
//    }
//}


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = FunctionMode.Default::class, name = "default"),
    JsonSubTypes.Type(value = FunctionMode.Named::class, name = "named")
)
sealed interface FunctionMode {

    @JvmInline
    @JsonDeserialize(using = DefaultDeserializer::class)
    @JsonSerialize(using = DefaultSerializer::class)
    value class Default(val value: String) : FunctionMode

    @JsonDeserialize(using = NamedDeserializer::class)
    @JsonSerialize(using = NamedSerializer::class)
    data class Named(val name: String) : FunctionMode

    companion object {
        val Auto: FunctionMode = Default("auto")
        val None: FunctionMode = Default("none")
    }
}


// Default 类的序列化器
class DefaultSerializer : JsonSerializer<FunctionMode.Default>() {
    override fun serialize(value: FunctionMode.Default, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(value.value)
    }
}

// Default 类的反序列化器
class DefaultDeserializer : JsonDeserializer<FunctionMode.Default>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): FunctionMode.Default {
        return FunctionMode.Default(p.valueAsString)
    }
}

// Named 类的序列化器
class NamedSerializer : JsonSerializer<FunctionMode.Named>() {
    override fun serialize(value: FunctionMode.Named, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeStringField("name", value.name)
        gen.writeEndObject()
    }
}

// Named 类的反序列化器
class NamedDeserializer : JsonDeserializer<FunctionMode.Named>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): FunctionMode.Named {
        val node = p.codec.readTree<JsonNode>(p)
        return FunctionMode.Named(node.get("name").asText())
    }
}