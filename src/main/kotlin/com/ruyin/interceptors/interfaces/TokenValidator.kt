package com.ruyin.interceptors.interfaces

interface TokenValidator {
    fun validate(token : String) : TokenClaim?
}