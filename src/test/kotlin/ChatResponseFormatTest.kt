import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lee.net.httpclient.safeJackson
import com.lee.openai.api.chat.ChatResponseFormat
import com.lee.openai.api.chat.ChatResponseFormatJsonConverter
import com.lee.openai.api.chat.JsonResponseSchema
import com.lee.openai.api.core.Parameters
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * 是全部通过的，只不过是部分空格或者换行格式有问题
 * 这样的话，就全部完成了
 */
class ChatResponseFormatTest {

    private val objectMapper = safeJackson

    @Test
    fun testJsonObjectFormat() {
        val format = ChatResponseFormat.JsonObject
        val json = objectMapper.writeValueAsString(format)
        assertEquals("""{"type":"json_object"}""", json)

        val deserialized = objectMapper.readValue<ChatResponseFormat>(json)
        assertEquals(format, deserialized)
    }

    @Test
    fun testTextFormat() {
        val format = ChatResponseFormat.Text
        val json = objectMapper.writeValueAsString(format)
        assertEquals("""{"type":"text"}""", json)

        val deserialized = objectMapper.readValue<ChatResponseFormat>(json)
        assertEquals(format, deserialized)
    }

    @Test
    fun testJsonSchemaFormat() {
        val schemaJson = """
            {
                "type": "object",
                "properties": {
                    "steps": {
                        "type": "array",
                        "items": {
                            "type": "object",
                            "properties": {
                                "explanation": {"type": "string"},
                                "output": {"type": "string"}
                            },
                            "required": ["explanation", "output"],
                            "additionalProperties": false
                        }
                    },
                    "final_answer": {"type": "string"}
                },
                "required": ["steps", "final_answer"],
                "additionalProperties": false
            }
        """.trimIndent()

        val parameters = Parameters.fromJsonString(schemaJson)
        val jsonSchema = JsonResponseSchema(name = "math_response", schema = parameters, strict = true)
        val format = ChatResponseFormat.jsonSchema(jsonSchema)

        val json = ChatResponseFormatJsonConverter.toJson(format)
//            objectMapper.writeValueAsString(format)
        val expectedJson = """
            {"type":"json_schema","json_schema":{"name":"math_response","schema":$schemaJson,"strict":true}}
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserialized = ChatResponseFormatJsonConverter.fromJson(json)
//            objectMapper.readValue<ChatResponseFormat>(json)
        assertEquals(format, deserialized)
//        assertEquals(format.jsonSchema?.schema?.schema, deserialized?.jsonSchema?.schema?.schema)
    }

    @Test
    fun testParametersBuildJsonObject() {
        val parameters = Parameters.buildJsonObject {
            put("type", "object")
            putObject("properties").apply {
                putObject("order_id").apply {
                    put("type", "string")
                    put("description", "The customer order ID.")
                }
            }
            putArray("required").add("order_id")
            put("additionalProperties", false)
        }

        val jsonSchema = JsonResponseSchema(name = "order", schema = parameters, strict = true)
        val format = ChatResponseFormat.jsonSchema(jsonSchema)

        val json = ChatResponseFormatJsonConverter.toJson(format)
//            objectMapper.writeValueAsString(format)
        val deserialized = ChatResponseFormatJsonConverter.fromJson(json)
//            objectMapper.readValue<ChatResponseFormat>(json)

        println("Json: $json")
        assertEquals(format, deserialized)
//        assertEquals(format.jsonSchema?.schema?.schema, deserialized?.jsonSchema?.schema?.schema)
    }
}