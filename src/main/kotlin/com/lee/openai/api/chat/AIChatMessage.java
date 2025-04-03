package com.lee.openai.api.chat;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.lee.openai.api.chat.msg.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "role",
        defaultImpl = AIChatMessage.class)
@JsonTypeName("ChatRequestMessage")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "system", value = AIChatSystemMessage.class),
        @JsonSubTypes.Type(name = "user", value = AIChatUserMessage.class),
        @JsonSubTypes.Type(name = "assistant", value = AIChatAssistantMessage.class),
        @JsonSubTypes.Type(name = "tool", value = AIChatToolMessage.class),
        @JsonSubTypes.Type(name = "function", value = AIChatFunctionMessage.class) })
public class AIChatMessage {

    public AIChatMessage() {
    }

}
