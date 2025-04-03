package com.lee.openai.api.thread

import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Thread identifier.
 */
@BetaOpenAI
@JvmInline
@Serializable
public value class ThreadId(public val id: String)
