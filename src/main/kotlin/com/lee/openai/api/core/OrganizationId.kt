package com.lee.openai.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Organization identifier.
 */
@Serializable
@JvmInline
public value class OrganizationId(public val id: String)
