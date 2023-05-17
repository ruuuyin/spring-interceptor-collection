package com.ruyin.interceptors.exception

class InvalidTokenException(override val message: String? = null) : RuntimeException(message) {
}