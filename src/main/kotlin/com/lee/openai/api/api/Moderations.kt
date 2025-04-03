package com.lee.openai.api.api

import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.moderation.ModerationRequest
import com.lee.openai.api.moderation.TextModeration

/**
 * Given an input text, outputs if the model classifies it as violating OpenAI's content policy.
 */
public interface Moderations {

    /**
     * Classifies if a text violates OpenAI's Content Policy.
     *
     * @param request moderation request.
     * @param requestOptions request options.
     */
    public suspend fun moderations(request: ModerationRequest, requestOptions: RequestOptions? = null): TextModeration
}
