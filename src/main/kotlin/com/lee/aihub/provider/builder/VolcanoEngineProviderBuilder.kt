package com.lee.aihub.provider.builder

import com.lee.aihub.ModelCapability
import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

/**
 * https://ark.cn-beijing.volces.com/api/v3/
 */
class VolcanoEngineProviderBuilder(id: String) : ProviderBuilder(id)  {

    init {
        source = "volcengine"
        baseUrl = "https://ark.cn-beijing.volces.com/api/v3/"
        isPaid = true
        tier = ModelTier.GIANT
        capabilities = setOf(ModelCapability.CHAT)
    }

    fun deepSeekR1()  =
        modelName("deepseek-r1-250120")
            .modelType("deepseek-r1")
            .tier(ModelTier.GIANT)
            .reasoning()
            .chat()
            .streaming()

}