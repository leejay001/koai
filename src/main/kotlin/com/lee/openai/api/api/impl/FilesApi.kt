package com.lee.openai.api.api.impl

import com.lee.net.http.extension.appendFileSource
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import com.lee.net.http.extension.perform
import com.lee.net.http.extension.requestOptions
import com.lee.net.http.HttpRequester
import com.lee.openai.api.api.Files
import com.lee.openai.api.core.DeleteResponse
import com.lee.openai.api.core.ListResponse
import com.lee.openai.api.core.RequestOptions
import com.lee.openai.api.file.File
import com.lee.openai.api.file.FileId
import com.lee.openai.api.file.FileUpload
import com.lee.openai.exception.*

/**
 * Implementation of [Files].
 */
internal class FilesApi(private val requester: HttpRequester) : Files {

    override suspend fun file(request: FileUpload, requestOptions: RequestOptions?): File {
        return requester.perform {
            it.submitFormWithBinaryData(url = ApiPath.Files, formData = formData {
                appendFileSource("file", request.file)
                append(key = "purpose", value = request.purpose.raw)
            }) {
                requestOptions(requestOptions)
            }
        }
    }

    override suspend fun files(requestOptions: RequestOptions?): List<File> {
        return requester.perform<ListResponse<File>> {
            it.get {
                url(path = ApiPath.Files)
                requestOptions(requestOptions)
            }
        }.data
    }

    override suspend fun file(fileId: FileId, requestOptions: RequestOptions?): File? {
        try {
            return requester.perform<HttpResponse> {
                it.get {
                    url(path = "${ApiPath.Files}/${fileId.id}")
                    requestOptions(requestOptions)
                }
            }.body()
        } catch (e: OpenAIAPIException) {
            if (e.statusCode == HttpStatusCode.NotFound.value) return null
            throw e
        }
    }

    override suspend fun delete(fileId: FileId, requestOptions: RequestOptions?): Boolean {
        val response = requester.perform<HttpResponse> {
            it.delete {
                url(path = "${ApiPath.Files}/${fileId.id}")
                requestOptions(requestOptions)
            }
        }

        return when (response.status) {
            HttpStatusCode.NotFound -> false
            else -> response.body<DeleteResponse>().deleted
        }
    }

    override suspend fun download(fileId: FileId, requestOptions: RequestOptions?): ByteArray {
        return requester.perform {
            it.get {
                url(path = "${ApiPath.Files}/${fileId.id}/content")
                requestOptions(requestOptions)
            }
        }
    }

}
