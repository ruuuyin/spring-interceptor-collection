package com.ruyin.interceptors.interfaces

import org.springframework.stereotype.Component

@Component
fun interface UserDetailService {
    fun getUserByUsername(username : String?) : UserDetails?
}