package com.lee.openai.api.vectorstore

import com.lee.openai.anotations.BetaOpenAI
import com.lee.openai.api.file.FileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A batch file request.
 */
@BetaOpenAI
@Serializable
public data class FileBatchRequest(

    /**
     * A list of File IDs that the vector store should use. Useful for tools like file_search that can access files.
     * [FileId]
     */
    @SerialName("file_ids") public val fileIds: List<String>,
)
