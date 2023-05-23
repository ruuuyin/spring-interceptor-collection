package com.ruyin.interceptors.annotation


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION,AnnotationTarget.TYPE)
annotation class Feature(
    val value : String = ""
)
