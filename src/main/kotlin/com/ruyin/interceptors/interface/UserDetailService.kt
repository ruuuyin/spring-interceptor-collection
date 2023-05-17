package com.ruyin.interceptors.`interface`

fun interface UserDetailService {
    fun getUserByUsername(username : String?) : UserDetails?
}