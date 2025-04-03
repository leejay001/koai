package com.lee.aihub.provider.builder

import com.lee.aihub.ModelCapability
import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

/**
 * sk-4231419e9345451d806b22e62f6cbf2e
 */
class DeepSeekProviderBuilder(id: String) : ProviderBuilder(id) {

    init {
        source = "deepseek"
        baseUrl = "https://api.deepseek.com/"
        isPaid = true
        tier = ModelTier.MEDIUM
        capabilities = setOf(ModelCapability.CHAT)
    }

    fun deepSeekV3() =
        modelName("deepseek-chat")
            .modelType("deepseek-chat")
            .tier(ModelTier.MEDIUM)
            .chat()
            .streaming()

    fun deepSeekR1() =
        modelName("deepseek-reasoner")
            .modelType("deepseek-r1")
            .tier(ModelTier.GIANT)
            .reasoning()
            .chat()
            .streaming()


}