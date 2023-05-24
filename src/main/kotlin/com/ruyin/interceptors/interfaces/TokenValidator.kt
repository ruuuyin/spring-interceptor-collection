package com.ruyin.interceptors.interfaces

import org.springframework.stereotype.Component

@Component
interface TokenValidator {
    fun validate(token : String) : TokenClaim?
}