package com.lee.openai.api.image

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.anotations.OpenAIDsl
import com.lee.openai.api.model.ModelId

/**
 * Image generation request.
 */
public class ImageCreation(
    /**
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    public val prompt: String,
    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    public val n: Int? = null,
    /**
     * The size of the generated images.
     * see [ImageSize]
     */
    public val size: String? = null,

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     */
    public val user: String? = null,

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     * see [ModelId]
     */
    public val model: String? = null,

    /**
     * The quality of the image that will be generated. `Quality.HD` creates images with finer details and greater
     * consistency across the image. This param is only supported for `dall-e-3`.
     * see [Quality]
     */
    public val quality: String? = null,

    /**
     * The style of the generated images. Must be one of [Style.Vivid] or `[Style.Natural]`. Vivid causes the model to
     * lean towards generating hyper-real and dramatic images. Natural causes the model to produce more natural, less
     * hyper-real looking images. This param is only supported for dall-e-3.
     * see [Style]
     */
    public val style: String? = null,
)

/**
 * Image generation request.
 */
@BetaOpenAI
public fun imageCreation(block: ImageCreationBuilder.() -> Unit): ImageCreation =
    ImageCreationBuilder().apply(block).build()

/**
 * Builder of [ImageCreation] instances.
 */
@BetaOpenAI
@OpenAIDsl
public class ImageCreationBuilder {

    /**
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    public var prompt: String? = null

    /**
     * The number of images to generate. Must be between 1 and 10.
     */
    public var n: Int? = null

    /**
     * The size of the generated images.
     */
    public var size: ImageSize? = null

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     */
    public var user: String? = null

    /**
     * The model used to generate image. Must be one of dall-e-2 or dall-e-3. If not provided, dall-e-2 is used.
     */
    public var model: ModelId? = null

    /**
     * Creates the [ImageCreation] instance
     */
    public fun build(): ImageCreation = ImageCreation(
        prompt = requireNotNull(prompt),
        n = n,
        size = size?.size,
        user = user,
        model = model?.id,
    )
}
