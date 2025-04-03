package com.lee.openai.api.file

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * File identifier.
 */
@Serializable
@JvmInline
public value class FileId(public val id: String)
