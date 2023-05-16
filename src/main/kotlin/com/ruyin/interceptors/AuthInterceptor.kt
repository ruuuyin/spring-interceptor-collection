package com.ruyin.interceptors

import com.ruyin.interceptors.annotation.Auth
import com.ruyin.interceptors.constant.ServletAttributeConstant
import com.ruyin.interceptors.exception.AuthException
import com.ruyin.interceptors.`interface`.TokenClaim
import com.ruyin.interceptors.`interface`.TokenValidator
import com.ruyin.interceptors.`interface`.UserDetailService
import com.ruyin.interceptors.`interface`.UserDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.AbstractPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.Objects

@Component
class AuthInterceptor @Autowired(required = false) constructor(private val passwordEncoder: PasswordEncoder,
                                                               private val tokenValidator: TokenValidator,
                                                               private val userDetailService : UserDetailService) : HandlerInterceptor {

    val authorizationPrefix: String = "Bearer"


    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val handlerMethod: HandlerMethod = handler as HandlerMethod

        val isAnnotationPresent = handlerMethod.method.declaringClass.isAnnotationPresent(Auth::class.java) ||
                handlerMethod.method.isAnnotationPresent(Auth::class.java)

        if (isAnnotationPresent) {
            val token: String = validateHeader(request)
            val tokenClaim : TokenClaim? = tokenValidator.validate(token)
            if (tokenClaim != null){
                val user : UserDetails = userDetailService.getUserByUsername(tokenClaim.getUsername())
                        .orElseThrow { throw AuthException("Invalid header token.") }

                request.setAttribute(ServletAttributeConstant.USER_AUTH_ATTRIB,user)
            }else{
                throw AuthException("Invalid header token.")
            }
        }

        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        super.postHandle(request, response, handler, modelAndView)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        super.afterCompletion(request, response, handler, ex)
    }


    fun validateHeader(request: HttpServletRequest): String {
        val authorizationHeader: String = request.getHeader("Authorization")

        if (Objects.isNull(authorizationHeader))
            throw AuthException("Missing \"Authorization\" header.")

        val token: List<String> = authorizationHeader.split(" ")
        if (!authorizationHeader.startsWith(authorizationPrefix) || token.size < 2)
            throw AuthException("Invalid Authorization header value.")

        return token[1]
    }
}