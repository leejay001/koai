package com.lee.openai.api.core

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public class Usage(
    /**
     * Count of prompts tokens.
     */
    @JsonProperty("prompt_tokens") public val promptTokens: Int = 0,
    /**
     * Count of completion tokens.
     */
    @JsonProperty("completion_tokens") public val completionTokens: Int = 0,
    /**
     * Count of total tokens.
     */
    @JsonProperty("total_tokens") public val totalTokens: Int = 0,

    @JsonProperty("completion_tokens_details") public val completionTokensDetails: CompletionTokensDetails? = null
)


/**
 * Details of completion tokens.
 */
public class CompletionTokensDetails(
    /**
     * Count of reasoning tokens.
     */
    @JsonProperty("reasoning_tokens") public val reasoningTokens: Int = 0,
    /**
     * Count of accepted prediction tokens.
     */
    @JsonProperty("accepted_prediction_tokens") public val acceptedPredictionTokens: Int = 0,
    /**
     * Count of rejected prediction tokens.
     */
    @JsonProperty("rejected_prediction_tokens") public val rejectedPredictionTokens: Int = 0,
)