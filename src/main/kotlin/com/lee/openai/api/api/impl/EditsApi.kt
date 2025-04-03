package com.lee.openai.api.api.impl

import com.lee.openai.api.api.Edits
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.HttpRequester
import com.lee.openai.api.edits.Edit
import com.lee.openai.api.edits.EditsRequest

/**
 * Implementation of [Edits]
 */
internal class EditsApi(private val requester: HttpRequester) : Edits {

    @Deprecated("Edits is deprecated. Chat completions is the recommend replacement.")
    override suspend fun edit(request: EditsRequest): Edit {
        return requester.perform {
            it.post {
                url(path = ApiPath.Edits)
                setBody(request)
                contentType(ContentType.Application.Json)
            }.body()
        }
    }
}
