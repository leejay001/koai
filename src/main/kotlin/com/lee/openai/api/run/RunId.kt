package com.lee.openai.api.run

import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A run id.
 */
@BetaOpenAI
@Serializable
@JvmInline
public value class RunId(public val id: String)
