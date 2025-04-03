package com.lee.openai.api.audio

import com.lee.openai.anotations.OpenAIDsl
import com.lee.openai.api.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Generates audio from the input text.
 */
@Serializable
public data class SpeechRequest(

    /**
     * One of the available TTS models: tts-1 or tts-1-hd
     * see [ModelId]
     */
    @SerialName("model") public val model: String,

    /**
     * The text to generate audio for. The maximum length is 4096 characters.
     */
    @SerialName("input") public val input: String,

    /**
     * The voice to use when generating the audio
     * see [Voice]
     */
    @SerialName("voice") public val voice: String? = null,

    /**
     * The format to audio in.
     * see [SpeechResponseFormat]
     */
    @SerialName("response_format") public val responseFormat: String? = null,

    /**
     * The speed of the generated audio. Select a value from 0.25 to 4.0. 1.0 is the default.
     */
    @SerialName("speed") public val speed: Double? = null,
)

/**
 * Creates a new [SpeechRequest] instance.
 */
public fun speechRequest(block: SpeechRequestBuilder.() -> Unit): SpeechRequest =
    SpeechRequestBuilder().apply(block).build()

/**
 * A speech request builder.
 */
@OpenAIDsl
public class SpeechRequestBuilder {

    /**
     * One of the available TTS models: tts-1 or tts-1-hd
     */
    public var model: ModelId? = null

    /**
     * The text to generate audio for. The maximum length is 4096 characters.
     */
    public var input: String? = null

    /**
     * The voice to use when generating the audio
     */
    public var voice: Voice? = null

    /**
     * The format to audio in.
     */
    public var responseFormat: SpeechResponseFormat? = null

    /**
     * The speed of the generated audio. Select a value from 0.25 to 4.0. 1.0 is the default.
     */
    public var speed: Double? = null

    /**
     * Builds and returns a [SpeechRequest] instance.
     */
    public fun build(): SpeechRequest = SpeechRequest(
        model = requireNotNull(model?.id) { "model is required" },
        input = requireNotNull(input) { "input is required" },
        voice = voice?.value,
        responseFormat = responseFormat?.value,
        speed = speed
    )
}
