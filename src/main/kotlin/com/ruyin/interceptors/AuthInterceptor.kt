package com.ruyin.interceptors

import com.ruyin.interceptors.annotation.Auth
import com.ruyin.interceptors.constant.ServletAttributeConstant
import com.ruyin.interceptors.exception.AuthException
import com.ruyin.interceptors.exception.InvalidTokenException
import com.ruyin.interceptors.`interface`.TokenClaim
import com.ruyin.interceptors.`interface`.TokenValidator
import com.ruyin.interceptors.`interface`.UserDetailService
import com.ruyin.interceptors.`interface`.UserDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.Objects

@Component
class AuthInterceptor @Autowired(required = false) constructor(private val tokenValidator: TokenValidator,
                                                               private val userDetailService : UserDetailService) : HandlerInterceptor {

    private val authorizationPrefix: String = "Bearer"


    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val handlerMethod: HandlerMethod = handler as HandlerMethod

        val isAnnotationPresent = handlerMethod.method.declaringClass.isAnnotationPresent(Auth::class.java) ||
                handlerMethod.method.isAnnotationPresent(Auth::class.java)

        try {
            if (isAnnotationPresent) {
                val token: String = validateHeader(request)
                val claim : TokenClaim = tokenValidator.validate(token)
                    ?: throw InvalidTokenException()

                val user : UserDetails = userDetailService.getUserByUsername(claim.getUsername())
                    ?: throw InvalidTokenException()

                if (user.isActive()) throw AuthException("Access denied.")

                request.setAttribute(ServletAttributeConstant.AUTHENTICATED_USER_ATTRIBUTE,user)
            }
        }catch (e: InvalidTokenException){
            throw InvalidTokenException("Invalid token.")
        }


        return super.preHandle(request, response, handler)
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