package com.lee.openai.api.api.impl

import io.ktor.client.request.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.api.api.Models
import com.lee.openai.api.core.ListResponse
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.model.Model
import com.lee.openai.api.model.ModelId

/**
 * Implementation of [Models] API.
 */
internal class ModelsApi(private val requester: HttpRequester) : Models {

    override suspend fun models(requestOptions: RequestOptions?): List<Model> {
        return requester.perform<ListResponse<Model>> {
            it.get {
                url(path = ApiPath.Models)
                requestOptions(requestOptions)
            }
        }.data
    }

    override suspend fun model(modelId: ModelId, requestOptions: RequestOptions?): Model {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Models}/${modelId.id}")
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }
        }
    }
}
