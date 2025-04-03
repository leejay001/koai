package com.lee.openai.api.chat.msg;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lee.openai.api.chat.AIChatMessage;

/**
 * A request chat message containing system instructions that influence how the model will generate a chat completions
 * response.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonTypeName("system")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AIChatSystemMessage extends AIChatMessage {

    /*
     * The contents of the system message.
     */
    @JsonProperty(value = "content")
    private String content;

    /*
     * An optional name for the participant.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Creates an instance of ChatRequestSystemMessage class.
     *
     * @param content the content value to set.
     */
    @JsonCreator
    public AIChatSystemMessage(@JsonProperty(value = "content") String content) {
        this.content = content;
    }

    /**
     * Get the content property: The contents of the system message.
     *
     * @return the content value.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Get the name property: An optional name for the participant.
     *
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: An optional name for the participant.
     *
     * @param name the name value to set.
     * @return the ChatRequestSystemMessage object itself.
     */
    public AIChatSystemMessage setName(String name) {
        this.name = name;
        return this;
    }

}
