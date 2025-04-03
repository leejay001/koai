package com.lee.openai.api.vectorstore

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.file.FileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A request to create a new vector store.
 */
@BetaOpenAI
@Serializable
public class VectorStoreRequest(
    /**
     * A list of File IDs that the vector store should use. Useful for tools like `file_search` that can access files.
     * This field can be part of creation requests, but not update requests.
     * [FileId]
     */
    @SerialName("file_ids") public val fileIds: List<String>? = null,

    /**
     * The name of the vector store.
     */
    @SerialName("name") public val name: String? = null,

    /**
     * The expiration policy for a vector store.
     */
    @SerialName("expires_after") public val expiresAfter: ExpirationPolicy? = null,

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional
     * information about the object in a structured format. Keys can be a maximum of 64 characters long, and values can
     * be a maximum of 512 characters long.
     */
    @SerialName("metadata") public val metadata: Map<String, String>? = null
)
