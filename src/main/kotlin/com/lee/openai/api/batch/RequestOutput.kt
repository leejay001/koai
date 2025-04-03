package com.lee.openai.api.batch

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.exception.OpenAIErrorDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The per-line object of the batch input file.
 */
@BetaOpenAI
@Serializable
public data class RequestOutput(

    /** The unique identifier of the request. */
    @SerialName("id") public val id: BatchId,

    /**
     * A developer-provided per-request id that will be used to match outputs to inputs.
     */
    @SerialName("custom_id") public val customId: CustomId,

    /**
     * The response.
     */
    @SerialName("response") public val response: ResponseOutput? = null,

    /**
     * For requests that failed with a non-HTTP error, this will contain more information on the cause of the failure.
     */
    @SerialName("error") public val error: OpenAIErrorDetails? = null,
)
