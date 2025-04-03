package com.lee.openai.api.assistant

import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * ID of an assistant.
 */
@BetaOpenAI
@Serializable
@JvmInline
public value class AssistantId(public val id: String)
