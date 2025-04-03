package com.lee.openai.api.chat.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lee.openai.api.chat.AIChatMessage;
import com.lee.openai.api.chat.FunctionCall;
import com.lee.openai.api.chat.ToolCall;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonTypeName("assistant")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AIChatAssistantMessage extends AIChatMessage {

    /*
     * The content of the message.
     */
    @JsonProperty(value = "content")
    private String content;

    /*
     * An optional name for the participant.
     */

    @JsonProperty(value = "name")
    private String name;

    /**
     * <a href="https://platform.openai.com/docs/guides/structured-outputs/how-to-use?context=without_parse">Refusals with Structured Outputs</a>
     * When using Structured Outputs with user-generated input, OpenAI models may occasionally refuse to fulfill the request for safety reasons.
     * Since a refusal does not necessarily follow the schema you have supplied in response_format,
     * the API response will include a new field called refusal to indicate that the model refused to fulfill the request.
     * <p>
     * When the refusal property appears in your output object,
     * you might present the refusal in your UI, or include conditional logic in code that consumes the response to handle the case of a refused request.
     * The API response from a refusal will look something like this:
     * "I'm sorry, I cannot assist with that request."
     */
    @JsonProperty(value = "refusal")
    private String refusal;

    /*
     * The tool calls that must be resolved and have their outputs appended to subsequent input messages for the chat
     * completions request to resolve as configured.
     */

    @JsonProperty(value = "tool_calls")
    private List<ToolCall> toolCalls;

    /*
     * The function call that must be resolved and have its output appended to subsequent input messages for the chat
     * completions request to resolve as configured.
     */

    @JsonProperty(value = "function_call")
    private FunctionCall functionCall;

    /**
     * Creates an instance of ChatRequestAssistantMessage class.
     *
     * @param content the content value to set.
     */

    @JsonCreator
    public AIChatAssistantMessage(@JsonProperty(value = "content") String content) {
        this.content = content;
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
     * @return the ChatRequestAssistantMessage object itself.
     */

    public AIChatAssistantMessage setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the toolCalls property: The tool calls that must be resolved and have their outputs appended to subsequent
     * input messages for the chat
     * completions request to resolve as configured.
     *
     * @return the toolCalls value.
     */

    public List<ToolCall> getToolCalls() {
        return this.toolCalls;
    }

    /**
     * Set the toolCalls property: The tool calls that must be resolved and have their outputs appended to subsequent
     * input messages for the chat
     * completions request to resolve as configured.
     *
     * @param toolCalls the toolCalls value to set.
     * @return the ChatRequestAssistantMessage object itself.
     */

    public AIChatAssistantMessage setToolCalls(List<ToolCall> toolCalls) {
        this.toolCalls = toolCalls;
        return this;
    }

    /**
     * Get the functionCall property: The function call that must be resolved and have its output appended to
     * subsequent input messages for the chat
     * completions request to resolve as configured.
     *
     * @return the functionCall value.
     */
    public FunctionCall getFunctionCall() {
        return this.functionCall;
    }

    /**
     * Set the functionCall property: The function call that must be resolved and have its output appended to
     * subsequent input messages for the chat
     * completions request to resolve as configured.
     *
     * @param functionCall the functionCall value to set.
     * @return the ChatRequestAssistantMessage object itself.
     */

    public AIChatAssistantMessage setFunctionCall(FunctionCall functionCall) {
        this.functionCall = functionCall;
        return this;
    }

    public AIChatAssistantMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public String getRefusal() {
        return refusal;
    }

    public void setRefusal(String refusal) {
        this.refusal = refusal;
    }
}
