package com.lee.openai.api.chat.msg

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.lee.openai.api.chat.AIChatMessage

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonTypeName("user")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
class AIChatUserMessage private constructor(
    @JsonProperty("content")
    @JsonDeserialize(using = MessageContentDeserializer::class)
    private val content: Any
) : AIChatMessage() {
    @JsonProperty("name")
    var name: String? = null

    companion object {
        @JvmStatic
        @JsonCreator
        fun fromContent(@JsonProperty("content") content: Any): AIChatUserMessage {
            return AIChatUserMessage(content)
        }
    }

    constructor(content: String) : this(content as Any)

    constructor(content: List<ChatMessageContentItem>) : this(content as Any)

    fun getContent(): Any = content
}


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    defaultImpl = ChatMessageContentItem::class
)
@JsonSubTypes(
    JsonSubTypes.Type(name = "text", value = ChatMessageTextContentItem::class),
    JsonSubTypes.Type(name = "image_url", value = ChatMessageImageContentItem::class)
)
sealed class ChatMessageContentItem

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("text")
class ChatMessageTextContentItem @JsonCreator constructor(
    @JsonProperty("text") val text: String,
    @JsonProperty("type") val type: String = "text"
) : ChatMessageContentItem()

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("image_url")
class ChatMessageImageContentItem @JsonCreator constructor(
    @JsonProperty("image_url") val imageUrl: ChatMessageImageUrl,
    @JsonProperty("type") val type: String = "image_url"
) : ChatMessageContentItem()

class ChatMessageImageUrl @JsonCreator constructor(
    @JsonProperty("url") private var url: String
) {
    @JsonProperty("detail")
    private var detail: ChatMessageImageDetailLevel? = ChatMessageImageDetailLevel.AUTO

    fun getUrl(): String = url

    fun getDetail(): ChatMessageImageDetailLevel? = detail

    fun setDetail(detail: ChatMessageImageDetailLevel?): ChatMessageImageUrl {
        this.detail = detail
        return this
    }
}

enum class ChatMessageImageDetailLevel(@JsonValue val value: String) {
    AUTO("auto"),
    LOW("low"),
    HIGH("high")
}

// 添加自定义反序列化器
class MessageContentDeserializer : JsonDeserializer<Any>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Any {
        val node = p.codec.readTree<JsonNode>(p)

        return when {
            // 如果是字符串
            node.isTextual -> node.textValue()
            // 如果是数组
            node.isArray -> {
                val mapper = p.codec as ObjectMapper
                val typeFactory = mapper.typeFactory
                val type = typeFactory.constructCollectionType(List::class.java, ChatMessageContentItem::class.java)
                mapper.convertValue(node, type)
            }
            // 如果是单个对象
            node.isObject -> {
                val mapper = p.codec as ObjectMapper
                mapper.treeToValue(node, ChatMessageContentItem::class.java)
            }
            else -> throw JsonMappingException(p, "Unexpected content type")
        }
    }
}