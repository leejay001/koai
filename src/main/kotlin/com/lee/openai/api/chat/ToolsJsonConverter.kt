package com.lee.openai.api.chat

import com.fasterxml.jackson.core.type.TypeReference
import com.lee.net.httpclient.safeJackson

object ToolsJsonConverter {

    fun toJson(tools: List<Tool>?): String? {
        return tools?.let { safeJackson.writeValueAsString(it) }
    }

    fun fromJson(json: String?): List<Tool>? {
        return json?.let { safeJackson.readValue(it,object : TypeReference<List<Tool>>() {}) }
    }

}