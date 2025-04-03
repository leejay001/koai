package com.lee.aihub.provider

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Extension to make delegation super easy
 */
fun <T> provider(initializer: () -> T): ReadOnlyProperty<Any?, T> {
    return ProviderDelegate(initializer)
}

/**
 * Lazy delegate for provider configurations
 */
class ProviderDelegate<T>(private val initializer: () -> T) : ReadOnlyProperty<Any?, T> {
    private val lazyValue by lazy(initializer)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = lazyValue
}