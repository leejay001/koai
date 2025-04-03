import com.fasterxml.jackson.databind.ObjectMapper
import com.lee.openai.api.core.Parameters

fun main() {
    val jsonString = """
    {
        "type": "object",
        "properties": {
            "order_id": {
                "type": "string",
                "description": "The customer order ID."
            }
        },
        "required": [
            "order_id"
        ],
        "additionalProperties": false
    }
    """.trimIndent()

    val parameters = Parameters.fromJsonString(jsonString)

    println("Deserialized Parameters:")
    println(ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(parameters.schema))

    // 验证反序列化的结果
    assert(parameters.schema["type"].asText() == "object")
    assert(parameters.schema["properties"]["order_id"]["type"].asText() == "string")
    assert(parameters.schema["properties"]["order_id"]["description"].asText() == "The customer order ID.")
    assert(parameters.schema["required"][0].asText() == "order_id")
    assert(parameters.schema["additionalProperties"].asBoolean() == false)

    println("All assertions passed. The JSON was correctly deserialized.")
}