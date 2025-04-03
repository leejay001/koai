package com.lee.openai.api.chat.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lee.openai.api.chat.AIChatMessage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonTypeName("tool")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AIChatToolMessage extends AIChatMessage {

    /*
     * The content of the message.
     */
    @JsonProperty(value = "content")
    private String content;

    /*
     * The ID of the tool call resolved by the provided content.
     */
    @JsonProperty(value = "tool_call_id")
    private String toolCallId;

    /**
     * Creates an instance of ChatRequestToolMessage class.
     *
     * @param content    the content value to set.
     * @param toolCallId the toolCallId value to set.
     */
    @JsonCreator
    public AIChatToolMessage(
            @JsonProperty(value = "content") String content,
            @JsonProperty(value = "tool_call_id") String toolCallId
    ) {
        this.content = content;
        this.toolCallId = toolCallId;
    }

    /**
     * Get the content property: The content of the message.
     *
     * @return the content value.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Get the toolCallId property: The ID of the tool call resolved by the provided content.
     *
     * @return the toolCallId value.
     */
    public String getToolCallId() {
        return this.toolCallId;
    }

}
