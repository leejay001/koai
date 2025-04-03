package com.lee.openai.api.image.internal

import com.lee.openai.anotations.InternalOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * The format in which the generated images are returned.
 */
@InternalOpenAI
@JvmInline
@Serializable
public value class ImageResponseFormat(public val format: String) {

    public companion object {

        /**
         * Response format as url.
         */
        public val url: ImageResponseFormat = ImageResponseFormat("url")

        /**
         * Response format as base 64 json.
         */
        public val base64Json: ImageResponseFormat = ImageResponseFormat("b64_json")
    }
}
