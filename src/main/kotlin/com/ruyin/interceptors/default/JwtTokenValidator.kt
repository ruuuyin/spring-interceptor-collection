package com.ruyin.interceptors.default

import com.ruyin.interceptors.interfaces.TokenClaim
import com.ruyin.interceptors.interfaces.TokenValidator

class JwtTokenValidator : TokenValidator {
    override fun validate(token: String): TokenClaim? {
        TODO("Not yet implemented")
    }
}