package com.lee.openai.api.api.impl

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.api.api.Moderations
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.moderation.ModerationRequest
import com.lee.openai.api.moderation.TextModeration

/**
 * Implementation of [Moderations].
 */
internal class ModerationsApi(private val requester: HttpRequester) : Moderations {

    override suspend fun moderations(request: ModerationRequest, requestOptions: RequestOptions?): TextModeration {
        return requester.perform {
            it.post {
                url(path = ApiPath.Moderations)
                setBody(request)
                contentType(ContentType.Application.Json)
                requestOptions(requestOptions)
            }.body()
        }
    }
}
