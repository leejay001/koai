package com.lee.openai.api.chat

import com.fasterxml.jackson.annotation.JsonProperty
import com.lee.openai.api.core.Usage
import com.lee.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An object containing a response from the chat stream completion api.
 *
 * [documentation](https://platform.openai.com/docs/api-reference/chat/create)
 */
public data class ChatCompletionChunk(
    /**
     * A unique id assigned to this completion
     */
    @JsonProperty("id")
    public var id: String? = null,

    /**
     * The creation time in epoch milliseconds.
     */
    @JsonProperty("created")
    public var created: Int? = null,

    /**
     * The model used.
     */
    @JsonProperty("model")
    public var model: String? = null,

    /**
     * A list of generated completions
     */
    @JsonProperty("choices")
    public var choices: List<ChatChunk>? = null,

    /**
     * Text completion usage data.
     */
    @JsonProperty("usage")
    public var usage: Usage? = null,

    /**
     * This fingerprint represents the backend configuration that the model runs with. Can be used in conjunction with
     * the `seed` request parameter to understand when backend changes have been made that might impact determinism.
     */
    @JsonProperty("system_fingerprint")
    public var systemFingerprint: String? = null,
)