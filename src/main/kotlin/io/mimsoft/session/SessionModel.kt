package io.mimsoft.session

data class SessionModel(
    val id: Long? = null,
    val uuid: String? = null,
    val sessionUuid: String? = null,
    val isExpired: Boolean? = false,
    val expiredTime: Long? = null,
    val createdAt: Long? = null,
    val adminId: Int? = null
)
