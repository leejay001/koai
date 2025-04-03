package com.lee.openai.api.batch

import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * The batch identifier.
 */
@BetaOpenAI
@JvmInline
@Serializable
public value class BatchId(public val id: String)
