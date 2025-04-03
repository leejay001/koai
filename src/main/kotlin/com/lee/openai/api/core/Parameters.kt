package com.lee.openai.api.core


///**
// * Represents parameters that a function accepts, described as a JSON Schema object.
// *
// * @property schema Json Schema object.
// */
//@Serializable(with = Parameters.JsonDataSerializer::class)
//public data class Parameters(public val schema: JsonElement) {
//
//    /**
//     * Custom serializer for the [Parameters] class.
//     */
//    public object JsonDataSerializer : KSerializer<Parameters> {
//        override val descriptor: SerialDescriptor = JsonElement.serializer().descriptor
//
//        /**
//         * Deserializes [Parameters] from JSON format.
//         */
//        override fun deserialize(decoder: Decoder): Parameters {
//            require(decoder is JsonDecoder) { "This decoder is not a JsonDecoder. Cannot deserialize `FunctionParameters`." }
//            return Parameters(decoder.decodeJsonElement())
//        }
//
//        /**
//         * Serializes [Parameters] to JSON format.
//         */
//        override fun serialize(encoder: Encoder, value: Parameters) {
//            require(encoder is JsonEncoder) { "This encoder is not a JsonEncoder. Cannot serialize `FunctionParameters`." }
//            encoder.encodeJsonElement(value.schema)
//        }
//    }
//
//    public companion object {
//
//        /**
//         * Creates a [Parameters] instance from a JSON string.
//         *
//         * @param json The JSON string to parse.
//         */
//        public fun fromJsonString(json: String): Parameters = Parameters(Json.parseToJsonElement(json))
//
//        /**
//         * Creates a [Parameters] instance using a [JsonObjectBuilder].
//         *
//         * @param block The [JsonObjectBuilder] to use.
//         */
//        public fun buildJsonObject(block: JsonObjectBuilder.() -> Unit): Parameters {
//            val json = kotlinx.serialization.json.buildJsonObject(block)
//            return Parameters(json)
//        }
//
//        /**
//         * Represents a no params function. Equivalent to:
//         * ```json
//         * {"type": "object", "properties": {}}
//         * ```
//         */
//        public val Empty: Parameters = buildJsonObject {
//            put("type", "object")
//            putJsonObject("properties") {}
//        }
//    }
//}


import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.node.ObjectNode

/**
 * 改成Jackson 的使用方式，给服务端使用，或者部分数据库组件，纯Kotlinx 不太支持
 *
 * {
 *                "type":"object",
 *                "properties":{
 *                   "order_id":{
 *                      "type":"string",
 *                      "description":"The customer order ID."
 *                   }
 *                },
 *                "required":[
 *                   "order_id"
 *                ],
 *                "additionalProperties":false
 *             }
 *
 */
data class Parameters(
    @JsonSerialize(using = ParametersSerializer::class)
    @JsonDeserialize(using = ParametersDeserializer::class)
    val schema: JsonNode
) {

    companion object {
        private val objectMapper = com.fasterxml.jackson.databind.ObjectMapper()

        fun fromJsonString(json: String): Parameters {
            val node = objectMapper.readTree(json)
            return Parameters(node)
        }

        /**
         * val params = Parameters.buildJsonObject {
         *     put("type", "object")
         *     putObject("properties").apply {
         *         put("exampleProperty", "value")
         *     }
         * }
         */
        fun buildJsonObject(block: ObjectNode.() -> Unit): Parameters {
            val node = objectMapper.createObjectNode() // Create an empty JSON object node
            node.block() // Apply the block to modify the node
            return Parameters(node)
        }

        val Empty: Parameters
            get() = Parameters(objectMapper.readTree("""{"type":"object","properties":{}}"""))
    }
}

class ParametersSerializer : JsonSerializer<Parameters>() {
    override fun serialize(
        value: Parameters,
        gen: com.fasterxml.jackson.core.JsonGenerator,
        serializers: SerializerProvider
    ) {
        gen.writeTree(value.schema)
    }
}

class ParametersDeserializer : JsonDeserializer<Parameters>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Parameters {
        val node = p.codec.readTree<JsonNode>(p)
        return Parameters(node)
    }
}

