package com.lee.openai.api.api

import com.lee.openai.api.completion.CompletionRequest
import com.lee.openai.api.completion.TextCompletion
import kotlinx.coroutines.flow.Flow

/**
 * Given a prompt, the model will return one or more predicted completions, and can also return the probabilities
 * of alternative tokens at each position.
 */
public interface Completions {

    /**
     * This is the main endpoint of the API. Returns the predicted completion for the given prompt,
     * and can also return the probabilities of alternative tokens at each position if requested.
     */
    @Deprecated("completions is deprecated, use chat completion instead")
    public suspend fun completion(request: CompletionRequest): TextCompletion

    /**
     * Stream variant of [completion].
     */
    @Deprecated("completions is deprecated, use chat completion instead")
    public fun completions(request: CompletionRequest): Flow<TextCompletion>
}
