package com.lee.aihub.provider.builder

import com.lee.aihub.ModelCapability
import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

/**
 * SiliconFlow provider builder
 */
class SiliconFlowProviderBuilder(id: String) : ProviderBuilder(id) {

    init {
        source = "siliconflow"
        baseUrl = "https://api.siliconflow.cn/v1/"
        isPaid = false
        tier = ModelTier.MEDIUM
        modelName = "deepseek-ai/DeepSeek-Coder-V2-Instruct"
        capabilities = setOf(ModelCapability.CHAT)
    }

}
