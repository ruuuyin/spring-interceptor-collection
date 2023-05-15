package com.ruyin.interceptors.`interface`

import java.util.Optional

interface UserDetailService {
    fun getUserByUsername(username : String?) : Optional<UserDetails>
}