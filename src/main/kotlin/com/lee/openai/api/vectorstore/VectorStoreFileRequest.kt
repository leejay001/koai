package com.lee.openai.api.vectorstore

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.file.FileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A request to create a vector store file by attaching a `File` to a vector store.
 */
@BetaOpenAI
@Serializable
public class VectorStoreFileRequest(
    /**
     * A File ID that the vector store should use. Useful for tools like `file_search` that can access files.
     * [FileId]
     */
    @SerialName("file_id") public val fileId: String? = null,
)
