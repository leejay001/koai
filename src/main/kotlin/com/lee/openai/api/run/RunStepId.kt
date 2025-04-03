package com.lee.openai.api.run

import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A run step id.
 */
@BetaOpenAI
@Serializable
@JvmInline
public value class RunStepId(public val id: String)
