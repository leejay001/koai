package com.lee.openai.api.chat.msg

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.lee.openai.api.chat.ChatRole
import com.lee.openai.api.chat.FunctionCall
import com.lee.openai.api.chat.ToolCall

/**
 *
 * https://platform.openai.com/docs/guides/function-calling
 *
 * response = Choice(
 *     finish_reason='tool_calls',
 *     index=0,
 *     logprobs=None,
 *     message=ChatCompletionMessage(
 *         content=None,
 *         role='assistant',
 *         function_call=None,
 *         tool_calls=[
 *             ChatCompletionMessageToolCall(
 *                 id='call_62136355',
 *                 function=Function(
 *                     arguments='{"city":"New York"}',
 *                     name='check_weather'),
 *                 type='function'),
 *             ChatCompletionMessageToolCall(
 *                 id='call_62136356',
 *                 function=Function(
 *                     arguments='{"city":"London"}',
 *                     name='check_weather'),
 *                 type='function'),
 *             ChatCompletionMessageToolCall(
 *                 id='call_62136357',
 *                 function=Function(
 *                     arguments='{"city":"Tokyo"}',
 *                     name='check_weather'),
 *                 type='function')
 *         ])
 * )
 *
 * # Iterate through tool calls to handle each weather check
 * for tool_call in response.message.tool_calls:
 *     arguments = json.loads(tool_call.function.arguments)
 *     city = arguments['city']
 *     weather_info = check_weather(city)
 *     print(f"Weather in {city}: {weather_info}")
 *
 *    返回的toolCalls 是一系列ToolCall
 */
class AIChatResponseMessage @JsonCreator constructor(
    @JsonProperty(value = "role") val role: ChatRole
){

    @JsonProperty(value = "tool_calls")
    var toolCalls: List<ToolCall>? = null

    @JsonProperty(value = "function_call")
    var functionCall: FunctionCall? = null

    @JsonProperty(value = "content")
    var content: String? = null

    /**
     * only for reasoning
     */
    @JsonProperty(value = "reasoning_content")
    var reasoningContent: String? = null

}

