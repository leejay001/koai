package com.lee.openai.api.image

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.anotations.OpenAIDsl
import com.lee.openai.api.file.FileSource
import com.lee.openai.api.model.ModelId

/**
 * Image variant request.
 */
public class ImageVariation(
    /**
     * The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     */
    public val image: FileSource,

    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    public val n: Int? = null,

    /**
     * The size of the generated images.
     */
    public val size: ImageSize? = null,

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    public val user: String? = null,

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    public val model: ModelId? = null,
)

/**
 * Image variant request.
 */
@BetaOpenAI
public fun imageVariation(block: ImageVariationBuilder.() -> Unit): ImageVariation =
    ImageVariationBuilder().apply(block).build()

/**
 * Builder of [ImageVariation] instances.
 */
@BetaOpenAI
@OpenAIDsl
public class ImageVariationBuilder {
    /**
     * The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     */
    public var image: FileSource? = null

    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    public var n: Int? = null

    /**
     * The size of the generated images.
     */
    public var size: ImageSize? = null

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    public var user: String? = null

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    public var model: ModelId? = null

    /**
     * Creates the [ImageVariation] instance
     */
    public fun build(): ImageVariation = ImageVariation(
        image = requireNotNull(image) { "image is required" },
        n = n,
        size = size,
        user = user,
        model = model,
    )
}
