package com.lee.openai.api.api.impl

import com.lee.net.http.extension.appendFileSource
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.anotations.InternalOpenAI
import com.lee.openai.api.api.Images
import com.lee.openai.api.core.ListResponse
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.image.*
import com.lee.openai.api.image.internal.ImageCreationRequest
import com.lee.openai.api.image.internal.ImageResponseFormat
import com.lee.openai.exception.*

internal class ImagesApi(private val requester: HttpRequester) : Images {

    @OptIn(InternalOpenAI::class)
    override suspend fun imageURL(creation: ImageCreation, requestOptions: RequestOptions?): List<ImageURL> {
        return requester.perform<ListResponse<ImageURL>> {
            it.post {
                url(path = ApiPath.ImagesGeneration)
                setBody(creation.toURLRequest())
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }
        }.data
    }

    @OptIn(InternalOpenAI::class)
    override suspend fun imageJSON(creation: ImageCreation, requestOptions: RequestOptions?): List<ImageJSON> {
        return requester.perform<ListResponse<ImageJSON>> {
            it.post {
                url(path = ApiPath.ImagesGeneration)
                setBody(creation.toJSONRequest())
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }
        }.data
    }

    @OptIn(InternalOpenAI::class)
    override suspend fun imageURL(edit: ImageEdit, requestOptions: RequestOptions?): List<ImageURL> {
        return requester.perform<ListResponse<ImageURL>> {
            it.submitFormWithBinaryData(
                url = ApiPath.ImagesEdits,
                formData = imageEditRequest(edit, ImageResponseFormat.url),
            ) {
                requestOptions(requestOptions)
            }
        }.data
    }

    @OptIn(InternalOpenAI::class)
    override suspend fun imageJSON(edit: ImageEdit, requestOptions: RequestOptions?): List<ImageJSON> {
        return requester.perform<ListResponse<ImageJSON>> {
            it.submitFormWithBinaryData(
                url = ApiPath.ImagesEdits,
                formData = imageEditRequest(edit, ImageResponseFormat.base64Json),
            ) {
                requestOptions(requestOptions)
            }
        }.data
    }

    /**
     * Build image edit request.
     */
    @OptIn(InternalOpenAI::class)
    private fun imageEditRequest(edit: ImageEdit, responseFormat: ImageResponseFormat) = formData {
        appendFileSource("image", edit.image)
        appendFileSource("mask", edit.mask)
        append(key = "prompt", value = edit.prompt)
        append(key = "response_format", value = responseFormat.format)
        edit.n?.let { n -> append(key = "n", value = n) }
        edit.size?.let { dim -> append(key = "size", value = dim.size) }
        edit.user?.let { user -> append(key = "user", value = user) }
        edit.model?.let { model -> append(key = "model", value = model.id) }
    }

    @OptIn(InternalOpenAI::class)
    override suspend fun imageURL(variation: ImageVariation, requestOptions: RequestOptions?): List<ImageURL> {
        return requester.perform<ListResponse<ImageURL>> {
            it.submitFormWithBinaryData(
                url = ApiPath.ImagesVariants,
                formData = imageVariantRequest(variation, ImageResponseFormat.url),
            ) {
                requestOptions(requestOptions)
            }
        }.data
    }

    @OptIn(InternalOpenAI::class)
    override suspend fun imageJSON(variation: ImageVariation, requestOptions: RequestOptions?): List<ImageJSON> {
        return requester.perform<ListResponse<ImageJSON>> {
            it.submitFormWithBinaryData(
                url = ApiPath.ImagesVariants,
                formData = imageVariantRequest(variation, ImageResponseFormat.base64Json),
            ) {
                requestOptions(requestOptions)
            }
        }.data
    }

    /**
     * Build image variant request.
     */
    @OptIn(InternalOpenAI::class)
    private fun imageVariantRequest(edit: ImageVariation, responseFormat: ImageResponseFormat) = formData {
        appendFileSource("image", edit.image)
        append(key = "response_format", value = responseFormat.format)
        edit.n?.let { n -> append(key = "n", value = n) }
        edit.size?.let { dim -> append(key = "size", value = dim.size) }
        edit.user?.let { user -> append(key = "user", value = user) }
        edit.model?.let { model -> append(key = "model", value = model.id) }
    }

    /** Convert [ImageCreation] instance to base64 JSON request */
    @OptIn(InternalOpenAI::class)
    private fun ImageCreation.toJSONRequest() = ImageCreationRequest(
        prompt = prompt,
        n = n,
        size = size,
        user = user,
        responseFormat = ImageResponseFormat.base64Json.format,
        model = model,
        quality = quality,
        style = style,
    )

    /** Convert [ImageCreation] instance to URL request */
    @OptIn(InternalOpenAI::class)
    private fun ImageCreation.toURLRequest() = ImageCreationRequest(
        prompt = prompt,
        n = n,
        size = size,
        user = user,
        responseFormat = ImageResponseFormat.url.format,
        model = model,
        quality = quality,
        style = style,
    )
}
