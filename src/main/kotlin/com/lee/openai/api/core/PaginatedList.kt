package com.lee.openai.api.core

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public data class PaginatedList<T>(
    @JsonProperty("data") val data: List<T>,
    @JsonProperty("has_more") val hasMore: Boolean? = null,
    @JsonProperty("first_id") val firstId: String? = null,
    @JsonProperty("last_id") val lastId: String? = null,
) : List<T> by data
