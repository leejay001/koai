package com.lee.openai.api.api

import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.embedding.EmbeddingRequest
import com.lee.openai.api.embedding.EmbeddingResponse

/**
 * Get a vector representation of a given input that can be easily consumed by machine learning models and algorithms.
 */
public interface Embeddings {

    /**
     * Creates an embedding vector representing the input text.
     *
     * @param request embedding request.
     * @param requestOptions request options.
     */
    public suspend fun embeddings(request: EmbeddingRequest, requestOptions: RequestOptions? = null): EmbeddingResponse
}
