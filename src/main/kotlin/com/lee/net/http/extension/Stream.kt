package com.lee.net.http.extension

import com.lee.net.httpclient.JsonLenient
import com.lee.net.httpclient.safeJackson
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.isActive
import kotlin.text.toByteArray

private const val STREAM_PREFIX = "data:"
private const val STREAM_END_TOKEN = "$STREAM_PREFIX [DONE]"

/**
 * Get data as [Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#Event_stream_format).
 */
internal suspend inline fun <reified T> FlowCollector<T>.streamEventsFrom(response: HttpResponse) {
    val channel: ByteReadChannel = response.body()
    try {
        while (currentCoroutineContext().isActive && !channel.isClosedForRead) {
            val line = channel.readUTF8Line() ?: continue
            val value: T = when {
                line.startsWith(STREAM_END_TOKEN) -> break
                line.startsWith(STREAM_PREFIX) -> safeJackson.readValue(line.removePrefix(STREAM_PREFIX), T::class.java)
                else -> continue
            }
            emit(value)
        }
    } finally {
        channel.cancel()
    }
}
