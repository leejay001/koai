package com.lee.openai.api.message

import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A message id.
 */
@BetaOpenAI
@Serializable
@JvmInline
public value class MessageId(public val id: String)
