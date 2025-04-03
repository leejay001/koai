package com.lee.openai.api.run

import com.lee.openai.anotations.BetaOpenAI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details on the action required to continue the run.
 */
@BetaOpenAI
@Serializable
public sealed interface RequiredAction {

    @Serializable
    @SerialName("submit_tool_outputs")
    public class SubmitToolOutputs(
        /**
         * A list of the relevant tool calls.
         */
        @SerialName("submit_tool_outputs") public val toolOutputs: ToolOutputs,
    ) : RequiredAction
}
