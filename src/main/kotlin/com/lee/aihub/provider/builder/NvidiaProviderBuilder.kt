package com.lee.aihub.provider.builder

import com.lee.aihub.ModelCapability
import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

/**
 * Nvidia provider builder
 */
class NvidiaProviderBuilder(id: String) : ProviderBuilder(id) {


    init {
        source = "nvidia"
        baseUrl = "https://integrate.api.nvidia.com/v1/"
        isPaid = false
        tier = ModelTier.MEDIUM
        capabilities = setOf(ModelCapability.CHAT)
    }

    fun deepSeekR1() =
        modelName("deepseek-ai/deepseek-r1")
            .modelType("deepseek-r1")
            .tier(ModelTier.GIANT)
            .reasoning()
            .chat()
            .streaming()


    fun llama3_2_90B() =
        baseUrl(baseUrl = "https://ai.api.nvidia.com/v1/gr/meta/llama-3.2-90b-vision-instruct/")
            .modelName(modelName = "meta/llama-3.2-90b-vision-instruct")
            .modelType(modelType = "llama-3.2-90b-vision")
            .tier(ModelTier.MEDIUM)
            .chat()
            .streaming()
            .vision()


    fun llama3_1_405B(): ProviderBuilder =
            modelName(modelName = "meta/llama-3.1-405b-instruct")
            .modelType(modelType = "llama-3.1-405b")
            .tier(ModelTier.MEDIUM)
            .chat()

    fun llama3_3_30B(): ProviderBuilder =
        modelName(modelName = "meta/llama-3.3-30b-instruct")
            .modelType(modelType = "llama-3.3-30b")
            .tier(ModelTier.MEDIUM)
            .chat()


}