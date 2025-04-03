package com.lee.openai.api.image.internal

import com.lee.openai.api.image.ImageSize
import com.lee.openai.api.image.Quality
import com.lee.openai.api.image.Style
import com.lee.openai.anotations.InternalOpenAI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Image generation request.
 * Results are expected as URLs.
 */
@Serializable
@InternalOpenAI
public data class ImageCreationRequest(
    @SerialName("prompt") val prompt: String,
    @SerialName("n") val n: Int? = null,
    /**
     * see [ImageSize]
     */
    @SerialName("size") val size: String? = null,
    @SerialName("user") val user: String? = null,

    /**
     * see [ImageResponseFormat]
     */
    @SerialName("response_format") val responseFormat: String,
    @SerialName("model") val model: String? = null,
    /**
     * see [Quality]
     */
    @SerialName("quality") val quality: String? = null,

    /**
     * see [Style]
     */
    @SerialName("style") val style: String? = null,
)
