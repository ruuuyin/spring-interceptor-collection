package com.ruyin.interceptors.`interface`

interface TokenValidator {
    fun validate(token : String) : TokenClaim?
}