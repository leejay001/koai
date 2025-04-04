package com.lee.openai.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * OpenAI's Model.
 */
@Serializable
public data class Model(
    /**
     * see [ModelId]
     */
    @SerialName("id") public val id: String,
    @SerialName("created") public val created: Long? = null,
    @SerialName("owned_by") public val ownedBy: String? = null,
    @SerialName("permission") public val permission: List<ModelPermission>? = null,
)
