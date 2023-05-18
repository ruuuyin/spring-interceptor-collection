package com.ruyin.interceptors.exception

open class FeatureNotAvailableException(override val message: String?) : RuntimeException(message) {
}