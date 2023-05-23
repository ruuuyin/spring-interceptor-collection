package com.ruyin.interceptors.interfaces

open interface UserDetails {
    fun getUsername() : String?
    fun getPassword() : String?
    fun isActive() : Boolean

}