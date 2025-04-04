package com.lee.openai.api.api.impl

///**
// * Implementation of [FineTunes].
// */
//internal class FineTunesApi(private val requester: HttpRequester) : FineTunes {
//
//    override suspend fun fineTune(request: FineTuneRequest): FineTune {
//        return requester.perform {
//            it.post {
//                url(path = ApiPath.FineTunes)
//                setBody(request)
//                contentType(ContentType.Application.Json)
//            }
//        }
//    }
//
//    override suspend fun fineTune(fineTuneId: FineTuneId): FineTune? {
//        val response = requester.perform<HttpResponse> {
//            it.get { url(path = "${ApiPath.FineTunes}/${fineTuneId.id}") }
//        }
//        return if (response.status == HttpStatusCode.NotFound) null else response.body()
//    }
//
//    override suspend fun fineTunes(): List<FineTune> {
//        return requester.perform<ListResponse<FineTune>> {
//            it.get { url(path = ApiPath.FineTunes) }
//        }.data
//    }
//
//    override suspend fun cancel(fineTuneId: FineTuneId): FineTune? {
//        val response = requester.perform<HttpResponse> {
//            it.post { url(path = "${ApiPath.FineTunes}/${fineTuneId.id}/cancel") }
//        }
//        return if (response.status == HttpStatusCode.NotFound) null else response.body()
//    }
//
//    override suspend fun fineTuneEvents(fineTuneId: FineTuneId): List<FineTuneEvent> {
//        return requester.perform<ListResponse<FineTuneEvent>> {
//            it.get { url(path = "${ApiPath.FineTunes}/${fineTuneId.id}/events") }
//        }.data
//    }
//
//    override fun fineTuneEventsFlow(fineTuneId: FineTuneId): Flow<FineTuneEvent> {
//        val request = HttpRequestBuilder().apply {
//            method = HttpMethod.Get
//            url(path = "${ApiPath.FineTunes}/${fineTuneId.id}/events") {
//                parameters.append("stream", "true")
//            }
//            accept(ContentType.Text.EventStream)
//            headers {
//                append(HttpHeaders.CacheControl, "no-cache")
//                append(HttpHeaders.Connection, "keep-alive")
//            }
//        }
//        return flow {
//            requester.perform(request) { response -> streamEventsFrom(response) }
//        }
//    }
//
//    override suspend fun delete(fineTuneModel: ModelId): Boolean {
//        val response = requester.perform<HttpResponse> {
//            it.delete {
//                url(path = "${ApiPath.Models}/${fineTuneModel.id}")
//            }
//        }
//        return when (response.status) {
//            HttpStatusCode.NotFound -> false
//            else -> response.body<DeleteResponse>().deleted
//        }
//    }
//}
