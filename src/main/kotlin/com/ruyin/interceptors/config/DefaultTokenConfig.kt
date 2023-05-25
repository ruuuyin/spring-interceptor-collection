package com.ruyin.interceptors.config

import com.ruyin.interceptors.AuthInterceptor
import com.ruyin.interceptors.default.JwtTokenValidator
import com.ruyin.interceptors.interfaces.TokenValidator
import com.ruyin.interceptors.interfaces.UserDetailService
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DefaultTokenConfig {
    @Bean
    @ConditionalOnMissingBean(TokenValidator::class)
    fun defaultTokenValidator() : TokenValidator{
        return JwtTokenValidator()
    }

    @Bean
    fun authInterceptor(tokenValidator: TokenValidator,userDetailService: UserDetailService) : AuthInterceptor{
        return AuthInterceptor(tokenValidator,userDetailService)
    }
}