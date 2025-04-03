package com.lee.openai.api.chat.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.lee.openai.api.chat.AIChatMessage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonTypeName("function")
public class AIChatFunctionMessage extends AIChatMessage {

    /*
     * The name of the function that was called to produce output.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * The output of the function as requested by the function call.
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     * Creates an instance of ChatRequestFunctionMessage class.
     *
     * @param name    the name value to set.
     * @param content the content value to set.
     */
    @JsonCreator
    public AIChatFunctionMessage(
            @JsonProperty(value = "name") String name,
            @JsonProperty(value = "content") String content
    ) {
        this.name = name;
        this.content = content;
    }

    /**
     * Get the name property: The name of the function that was called to produce output.
     *
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the content property: The output of the function as requested by the function call.
     *
     * @return the content value.
     */
    public String getContent() {
        return this.content;
    }

}
