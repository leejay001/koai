package com.lee.openai.api.chat

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.lee.openai.api.core.Parameters
import kotlinx.serialization.SerialName

/**
 * An object specifying the format that the model must output.
 *
 * 添加新的：
 * {
 *    "type":"json_schema",
 *    "json_schema":{
 *       "name":"math_response",
 *       "schema":{
 *          "type":"object",
 *          "properties":{
 *             "steps":{
 *                "type":"array",
 *                "items":{
 *                   "type":"object",
 *                   "properties":{
 *                      "explanation":{
 *                         "type":"string"
 *                      },
 *                      "output":{
 *                         "type":"string"
 *                      }
 *                   },
 *                   "required":[
 *                      "explanation",
 *                      "output"
 *                   ],
 *                   "additionalProperties":false
 *                }
 *             },
 *             "final_answer":{
 *                "type":"string"
 *             }
 *          },
 *          "required":[
 *             "steps",
 *             "final_answer"
 *          ],
 *          "additionalProperties":false
 *       },
 *       "strict":true
 *    }
 * }
 *
 */
@JsonSerialize(using = ChatResponseFormatSerializer::class)
@JsonDeserialize(using = ChatResponseFormatDeserializer::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public data class ChatResponseFormat(
    /**
     * Response format type.
     */
    @SerialName("type") val type: String,
    @JsonProperty("json_schema") val jsonSchema: JsonResponseSchema? = null
) {

    public companion object {
        /**
         * JSON mode, which guarantees the message the model generates, is valid JSON.
         */
        public val JsonObject: ChatResponseFormat = ChatResponseFormat(type = "json_object")

        /**
         * Default text mode.
         */
        public val Text: ChatResponseFormat = ChatResponseFormat(type = "text")


        fun jsonSchema(schema: JsonResponseSchema) = ChatResponseFormat(type = "json_schema", jsonSchema = schema)

    }
}

data class JsonResponseSchema(
    @JsonProperty("name") val name: String,
    @JsonProperty("schema") val schema: Parameters,
    @JsonProperty("strict") val strict: Boolean
)

class ChatResponseFormatSerializer : JsonSerializer<ChatResponseFormat>() {
    override fun serialize(value: ChatResponseFormat, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeStringField("type", value.type)
        if (value.type == "json_schema" && value.jsonSchema != null) {
            gen.writeObjectField("json_schema", value.jsonSchema)
        }
        gen.writeEndObject()
    }
}

class ChatResponseFormatDeserializer : JsonDeserializer<ChatResponseFormat>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ChatResponseFormat {
        val node: JsonNode = p.codec.readTree(p)
        val type = node.get("type").asText()
        return when (type) {
            "json_object" -> ChatResponseFormat.JsonObject
            "text" -> ChatResponseFormat.Text
            "json_schema" -> {
                val jsonSchemaNode = node.get("json_schema")
                val jsonSchema = JsonResponseSchema(
                    name = jsonSchemaNode.get("name").asText(),
                    schema = Parameters(jsonSchemaNode.get("schema")),
                    strict = jsonSchemaNode.get("strict").asBoolean()
                )
                ChatResponseFormat.jsonSchema(jsonSchema)
            }
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }
}
