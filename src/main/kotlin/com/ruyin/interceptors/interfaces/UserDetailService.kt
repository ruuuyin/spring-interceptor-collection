package com.ruyin.interceptors.interfaces

fun interface UserDetailService {
    fun getUserByUsername(username : String?) : UserDetails?
}