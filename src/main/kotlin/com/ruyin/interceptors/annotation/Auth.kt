package com.ruyin.interceptors.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Service

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE,AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Auth(
    @get:AliasFor(annotation = Service::class)
    val value : String = ""
)
