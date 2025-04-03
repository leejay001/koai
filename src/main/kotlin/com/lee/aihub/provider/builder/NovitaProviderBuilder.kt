package com.lee.aihub.provider.builder

import com.lee.aihub.ModelTier
import com.lee.aihub.ProviderBuilder

/**
 * main github: sk_ZC4fbj39mniZ8sSZETu3aV5zrjvddqehqWBHHfsaczU
 * sub gitub: sk_HhkNlSPPWjkorFA2P0PuchN0JEj1hOm8DbOeWGQIX5Y
 * andev235: sk_Ve-DW08_TaT6-qsgjclrELEc9DHRU7yteMm9rzGjIMc
 */
class NovitaProviderBuilder(id: String) : ProviderBuilder(id) {

    init {
        source = "novita"
        baseUrl = "https://api.novita.ai/v3/openai/"
        isPaid = false
        tier = ModelTier.GIANT
    }

    fun deepseek() =
        modelName("deepseek/deepseek-r1")
            .modelType("deepseek-r1")
            .tier(ModelTier.GIANT)
            .chat()
            .streaming()
            .reasoning()

}