package com.lee.openai.api

import com.lee.openai.api.api.impl.ModelsApi
import com.lee.net.http.HttpRequester
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.api.*
import com.lee.openai.api.api.impl.*
import com.lee.openai.api.api.impl.AssistantsApi
import com.lee.openai.api.api.impl.AudioApi
import com.lee.openai.api.api.impl.BatchApi
import com.lee.openai.api.api.impl.ChatApi
import com.lee.openai.api.api.impl.CompletionsApi
import com.lee.openai.api.api.impl.EditsApi
import com.lee.openai.api.api.impl.EmbeddingsApi
import com.lee.openai.api.api.impl.FilesApi
import com.lee.openai.api.api.impl.FineTuningApi
import io.ktor.utils.io.core.*

/**
 *     FineTunes by FineTunesApi(requester),
 */
@OptIn(BetaOpenAI::class)
class OpenAIApiImpl(
    private val requester: HttpRequester
) : OpenAI,
    Completions by CompletionsApi(requester),
    Files by FilesApi(requester),
    Edits by EditsApi(requester),
    Embeddings by EmbeddingsApi(requester),
    Models by ModelsApi(requester),
    Moderations by ModerationsApi(requester),
    Images by ImagesApi(requester),
    Chat by ChatApi(requester),
    Audio by AudioApi(requester),
    FineTuning by FineTuningApi(requester),
    Assistants by AssistantsApi(requester),
    Threads by ThreadsApi(requester),
    Runs by RunsApi(requester),
    Messages by MessagesApi(requester),
    VectorStores by VectorStoresApi(requester),
    Batch by BatchApi(requester),
    Closeable by requester