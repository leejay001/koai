package com.lee.openai.anotations

/**
 * A marker annotations for DSLs.
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class OpenAIDsl

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION,AnnotationTarget.FIELD)
public annotation class AzureAIDsl
