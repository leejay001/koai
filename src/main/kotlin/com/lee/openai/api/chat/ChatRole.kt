package com.lee.openai.api.chat

import com.fasterxml.jackson.annotation.JsonValue

enum class ChatRole(@JsonValue val value: String) {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    FUNCTION("function"),
    TOOL("tool")
}