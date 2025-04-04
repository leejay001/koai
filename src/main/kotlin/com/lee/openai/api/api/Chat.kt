package com.lee.openai.api.api

import com.lee.openai.api.chat.ChatCompletion
import com.lee.openai.api.chat.ChatCompletionChunk
import com.lee.openai.api.chat.ChatCompletionRequest
import com.lee.openai.api.core.RequestOptions
import kotlinx.coroutines.flow.Flow

/**
 * Given a chat conversation, the model will return a chat completion response.
 */
public interface Chat {

    /**
     * Creates a completion for the chat message.
     *
     * @param request completion request.
     * @param requestOptions request options.
     */
    public suspend fun chatCompletion(
        request: ChatCompletionRequest,
        requestOptions: RequestOptions? = null
    ): ChatCompletion

    /**
     * Stream variant of [chatCompletion].
     *
     * @param request completion request.
     * @param requestOptions request options.
     */
    public fun chatCompletions(
        request: ChatCompletionRequest,
        requestOptions: RequestOptions? = null
    ): Flow<ChatCompletionChunk>
}
