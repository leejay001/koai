package com.lee.openai.api.completion

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.lee.openai.anotations.OpenAIDsl
import com.lee.openai.api.model.ModelId
import kotlinx.serialization.Serializable

/**
 * A request for OpenAI to generate a predicted completion for a prompt.
 * All fields are Optional.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/create-completion)
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CompletionRequest(

    /**
     * ID of the model to use.
     * see [ModelId]
     */
    @JsonProperty("model") public val model: String,

    @JsonProperty("reasoning_effort") public val reasoningEffort: String? = null,

    /**
     * The prompt(s) to generate completions for, encoded as a string, a list of strings, or a list of token lists.
     *
     * Note that `<|endoftext|>` is the document separator that the model sees during training, so if a prompt is not
     * specified the model will generate as if from the beginning of a new document.
     *
     * Defaults to `<|endoftext|>`.
     */
    @JsonProperty("prompt") public val prompt: String? = null,

    /**
     * The maximum number of tokens to generate.
     * Requests can use up to 2048 tokens shared between prompt and completion.
     * (One token is roughly 4 characters for normal English text)
     *
     * Defaults to 16.
     */
    @JsonProperty("max_tokens") public val maxTokens: Int? = null,

    /**
     * What sampling temperature to use. Higher values means the model will take more risks.
     * Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
     *
     * We generally recommend using this or [topP] but not both.
     *
     * Defaults to 1.
     */
    @JsonProperty("temperature") public val temperature: Double? = null,

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of
     * the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are
     * considered.
     *
     * We generally recommend using this or [temperature] but not both.
     *
     * Defaults to 1.
     */
    @JsonProperty("top_p") public val topP: Double? = null,

    /**
     * How many completions to generate for each prompt.
     *
     * **Note:** Because this parameter generates many completions, it can quickly consume your token quota.
     * Use carefully and ensure that you have reasonable settings for [maxTokens] and [stop].
     *
     * Defaults to 1.
     */
    @JsonProperty("n") public val n: Int? = null,

    /**
     * Include the log probabilities on the [logprobs] most likely tokens, as well the chosen tokens.
     * For example, if [logprobs] is 10, the API will return a list of the 10 most likely tokens.
     * The API will always return the logprob of the sampled token, so there may be up to [logprobs]+1 elements
     * in the response.
     *
     * Defaults to `null`.
     */
    @JsonProperty("logprobs") public val logprobs: Int? = null,

    /**
     * Echo back the prompt in addition to the completion.
     *
     * Defaults to `false`.
     */
    @JsonProperty("echo") public val echo: Boolean? = null,

    /**
     * Up to 4 sequences where the API will stop generating further tokens.
     * The returned text will not contain the stop sequence.
     *
     * Defaults to `null`.
     */
    @JsonProperty("stop") public val stop: List<String>? = null,

    /**
     * Number between 0 and 1 (default 0) that penalizes new tokens based on whether they appear in the text so far.
     * Increases the model's likelihood to talk about new topics.
     *
     * Defaults to 0.
     */
    @JsonProperty("presence_penalty") public val presencePenalty: Double? = null,

    /**
     * Number between 0 and 1 (default 0) that penalizes new tokens based on their existing frequency in the text so far.
     * Decreases the model's likelihood to repeat the same line verbatim.
     *
     * Defaults to 0.
     */
    @JsonProperty("frequency_penalty") public val frequencyPenalty: Double? = null,

    /**
     * Generates [bestOf] completions server-side and returns the "best"
     * (the one with the lowest log probability per token). Results cannot be streamed.
     *
     * When used with [n], [bestOf] controls the number of candidate completions and [n] specifies how many to return,
     * [bestOf] must be greater than [n].
     *
     * **Note:** Because this parameter generates many completions, it can quickly consume your token quota.
     * Use carefully and ensure that you have reasonable settings for [maxTokens] and [stop].
     *
     * Defaults to 1
     */
    @JsonProperty("best_of") public val bestOf: Int? = null,

    /**
     * Modify the likelihood of specified tokens appearing in the completion.
     *
     * Accepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias`
     * value from -100 to 100. You can use this tokenizer tool (which works for both GPT-2 and GPT-3) to convert text
     * to token IDs. Mathematically, the bias is added to the logits generated by the model prior to sampling.
     * The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood
     * of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
     *
     * As an example, you can pass `{"50256": -100}` to prevent the `<|endoftext|> token from being generated.
     *
     * Defaults to `null`.
     */
    @JsonProperty("logit_bias") public val logitBias: Map<String, Int>? = null,

    /**
     * A unique identifier representing your end-user, which will help OpenAI to monitor and detect abuse.
     */
    @JsonProperty("user") public val user: String? = null,

    /**
     * The suffix that comes after a completion of inserted text.
     */
    @JsonProperty("suffix") public val suffix: String? = null,
)

/**
 * A request for OpenAI to generate a predicted completion for a prompt.
 * All fields are Optional.
 *
 * [documentation](https://beta.openai.com/docs/api-reference/create-completion)
 */
public fun completionRequest(block: CompletionRequestBuilder.() -> Unit): CompletionRequest =
    CompletionRequestBuilder().apply(block).build()

/**
 * Builder of [CompletionRequest] instances.
 */
@OpenAIDsl
public class CompletionRequestBuilder {

    /**
     * ID of the model to use.
     */
    public var model: ModelId? = null

    /**
     * The prompt(s) to generate completions for, encoded as a string, a list of strings, or a list of token lists.
     *
     * Note that `<|endoftext|>` is the document separator that the model sees during training, so if a prompt is not
     * specified the model will generate as if from the beginning of a new document.
     *
     * Defaults to `<|endoftext|>`.
     */
    public var prompt: String? = null

    /**
     * The maximum number of tokens to generate.
     * Requests can use up to 2048 tokens shared between prompt and completion.
     * (One token is roughly 4 characters for normal English text)
     *
     * Defaults to 16.
     */
    public var maxTokens: Int? = null

    /**
     * What sampling temperature to use. Higher values means the model will take more risks.
     * Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
     *
     * We generally recommend using this or [topP] but not both.
     *
     * Defaults to 1.
     */
    public var temperature: Double? = null

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of
     * the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are
     * considered.
     *
     * We generally recommend using this or [temperature] but not both.
     *
     * Defaults to 1.
     */
    public var topP: Double? = null

    /**
     * How many completions to generate for each prompt.
     *
     * **Note:** Because this parameter generates many completions, it can quickly consume your token quota.
     * Use carefully and ensure that you have reasonable settings for [maxTokens] and [stop].
     *
     * Defaults to 1.
     */
    public var n: Int? = null

    /**
     * Include the log probabilities on the [logprobs] most likely tokens, as well the chosen tokens.
     * For example, if [logprobs] is 10, the API will return a list of the 10 most likely tokens.
     * The API will always return the logprob of the sampled token, so there may be up to [logprobs]+1 elements
     * in the response.
     *
     * Defaults to `null`.
     */
    public var logprobs: Int? = null

    /**
     * Echo back the prompt in addition to the completion.
     *
     * Defaults to `false`.
     */
    public var echo: Boolean? = null

    /**
     * Up to 4 sequences where the API will stop generating further tokens.
     * The returned text will not contain the stop sequence.
     *
     * Defaults to `null`.
     */
    public var stop: List<String>? = null

    /**
     * Number between 0 and 1 (default 0) that penalizes new tokens based on whether they appear in the text so far.
     * Increases the model's likelihood to talk about new topics.
     *
     * Defaults to 0.
     */
    public var presencePenalty: Double? = null

    /**
     * Number between 0 and 1 (default 0) that penalizes new tokens based on their existing frequency in the text so far.
     * Decreases the model's likelihood to repeat the same line verbatim.
     *
     * Defaults to 0.
     */
    public var frequencyPenalty: Double? = null

    /**
     * Generates [bestOf] completions server-side and returns the "best"
     * (the one with the lowest log probability per token). Results cannot be streamed.
     *
     * When used with [n], [bestOf] controls the number of candidate completions and [n] specifies how many to return,
     * [bestOf] must be greater than [n].
     *
     * **Note:** Because this parameter generates many completions, it can quickly consume your token quota.
     * Use carefully and ensure that you have reasonable settings for [maxTokens] and [stop].
     *
     * Defaults to 1
     */
    public var bestOf: Int? = null

    /**
     * Modify the likelihood of specified tokens appearing in the completion.
     *
     * Accepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias`
     * value from -100 to 100. You can use this tokenizer tool (which works for both GPT-2 and GPT-3) to convert text
     * to token IDs. Mathematically, the bias is added to the logits generated by the model prior to sampling.
     * The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood
     * of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
     *
     * As an example, you can pass `{"50256": -100}` to prevent the `<|endoftext|> token from being generated.
     *
     * Defaults to `null`.
     */
    public var logitBias: Map<String, Int>? = null

    /**
     * A unique identifier representing your end-user, which will help OpenAI to monitor and detect abuse.
     */
    public var user: String? = null

    /**
     * The suffix that comes after a completion of inserted text.
     */
    public var suffix: String? = null

    /**
     * Create [CompletionRequest] instance.
     */
    public fun build(): CompletionRequest = CompletionRequest(
        model = requireNotNull(model?.id) { "model is required" },
        reasoningEffort = null,
        prompt = prompt,
        maxTokens = maxTokens,
        temperature = temperature,
        topP = topP,
        n = n,
        logprobs = logprobs,
        echo = echo,
        stop = stop,
        presencePenalty = presencePenalty,
        frequencyPenalty = frequencyPenalty,
        bestOf = bestOf,
        logitBias = logitBias,
        user = user,
        suffix = suffix,
    )
}
