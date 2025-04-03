package com.lee.openai.api.completion

import com.lee.openai.api.core.FinishReason
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A completion generated by GPT-3.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/create-completion)
 */
@Serializable
public data class Choice(

    /**
     * The generated text. Will include the prompt if [CompletionRequest.echo] is true
     */
    @SerialName("text")
    public val text: String,

    /**
     * This index of this completion in the returned list.
     */
    @SerialName("index")
    public val index: Int,

    /**
     * The log probabilities of the chosen tokens and the top [CompletionRequest.logprobs] tokens.
     */
    @SerialName("logprobs")
    @Deprecated("removed from the response")
    public val logprobs: Logprobs? = null,

    /**
     * The reason the model stopped generating tokens. This will be [FinishReason.Stop] if the model hit a natural stop
     * point or a provided stop sequence, or [FinishReason.Length] if the maximum number of tokens specified in the
     * request was reached.
     * see [FinishReason]
     */
    @SerialName("finish_reason")
    public val finishReason: String? = null,
)
