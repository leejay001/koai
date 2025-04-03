package com.lee.openai.api.api

import com.lee.openai.api.edits.Edit
import com.lee.openai.api.edits.EditsRequest

/**
 * Given a prompt and an instruction, the model will return an edited version of the prompt.
 */
public interface Edits {

    /**
     * Creates a new edit for the provided input, instruction, and parameters.
     */
    @Deprecated("Edits is deprecated. Chat completions is the recommend replacement.")
    public suspend fun edit(request: EditsRequest): Edit
}
