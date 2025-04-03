import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.lee.net.httpclient.safeJackson
import com.lee.openai.api.chat.Tool
import com.lee.openai.api.chat.ToolsJsonConverter
import com.lee.openai.api.core.Parameters
import com.lee.openai.api.core.ParametersDeserializer

fun main() {
    val jsonString = """
    [
        {
            "type": "function",
            "function": {
                "name": "get_delivery_date",
                "description": "Get the delivery date for a customer's order. Call this whenever you need to know the delivery date, for example when a customer asks 'Where is my package'",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "order_id": {
                            "type": "string",
                            "description": "The customer's order ID."
                        }
                    },
                    "required": ["order_id"],
                    "additionalProperties": false
                }
            },
            "strict": true
        }
    ]
    """.trimIndent()

    val objectMapper = safeJackson

    val tools: List<Tool> = ToolsJsonConverter.fromJson(jsonString) ?: throw NullPointerException("failed")

    println("Deserialized Tool List:")
    tools.forEachIndexed { index, tool ->
        println("Tool $index:")
        println("  Type: ${tool.type}")
        println("  Function:")
        println("    Name: ${tool.function.name}")
        println("    Description: ${tool.function.description}")
        println("Paramters real: ${tool.function.parameters}")
        println("    Parameters: ${objectMapper.writeValueAsString(tool.function.parameters)}")
        println("  Strict: ${tool.strict}")
    }

    // Verify deserialization
    assert(tools.size == 1)
    assert(tools[0].type == "function")
    assert(tools[0].function.name == "get_delivery_date")
    assert(tools[0].function.description == "Get the delivery date for a customer's order. Call this whenever you need to know the delivery date, for example when a customer asks 'Where is my package'")
    assert(tools[0].strict == true)

    val jsonStringConverted = ToolsJsonConverter.toJson(tools)
    println("    ConvertedJson: $jsonStringConverted")
    assert(jsonStringConverted == jsonString)


    println("All assertions passed. The JSON was correctly deserialized into a List<Tool>.")
}


//fun main() {
//
//    val jsonString = """
//    {
//        "type": "function",
//        "function": {
//            "name": "get_delivery_date",
//            "description": "Get the delivery date for a customer's order.",
//            "parameters": {
//                "type": "object",
//                "properties": {
//                    "order_id": {
//                        "type": "string",
//                        "description": "The customer's order ID."
//                    }
//                },
//                "required": ["order_id"],
//                "additionalProperties": false
//            }
//        },
//        "strict": true
//    }
//    """.trimIndent()
//
//
//    val objectMapper = safeJackson
//
//    val tool: Tool = objectMapper.readValue(jsonString, Tool::class.java)
//
//    println("Deserialized Tool:")
//    println("Type: ${tool.type}")
//    println("Function Name: ${tool.function.name}")
//    println("Function Description: ${tool.function.description}")
//    println("Real Parameters: ${tool.function.parameters}")
//    println("Parameters: ${objectMapper.writeValueAsString(tool.function.parameters)}")
//    println("Strict: ${tool.strict}")
//}