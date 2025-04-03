package com.lee.openai.api.chat

import com.lee.net.httpclient.safeJackson

object ChatResponseFormatJsonConverter {

    fun toJson(format: ChatResponseFormat?): String? {
        return format?.let { safeJackson.writeValueAsString(it) }
    }

    fun fromJson(json: String?): ChatResponseFormat? {
        return json?.let { safeJackson.readValue(it, ChatResponseFormat::class.java) }
    }

}