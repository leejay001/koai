package com.lee.openai.api.chat.internal

//import com.lee.openai.api.chat.Content
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

/**
 * Serializer for [Content].
 */
//internal class ContentSerializer : JsonContentPolymorphicSerializer<Content>(Content::class) {
//    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Content> {
//        return when (element) {
//            is JsonPrimitive -> TextContent.serializer()
//            is JsonArray -> ListContent.serializer()
//            else -> throw SerializationException("Unsupported JSON element: $element")
//        }
//    }
//}
