package com.lee.openai.api

import com.lee.net.http.HttpTransport
import com.lee.net.httpclient.OpenAIConfig
import com.lee.net.httpclient.createHttpClient
import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.api.*
import io.ktor.client.*
import io.ktor.utils.io.core.*

// FineTune not now
@OptIn(BetaOpenAI::class)
public interface OpenAI : Completions, Files, Edits, Embeddings, Models, Moderations, Images, Chat, Audio,
    FineTuning, Assistants, Threads, Runs, Messages, VectorStores, Closeable

public fun openai(config: OpenAIConfig): OpenAI {
    val httpClient: HttpClient by createHttpClient(config)
    val transport = HttpTransport(httpClient)
    return OpenAIApiImpl(transport)
}