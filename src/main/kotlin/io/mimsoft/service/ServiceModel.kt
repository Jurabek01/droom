package io.mimsoft.service

import io.mimsoft.utils.ContentModel

data class ServiceModel(
    val id: Long? = null,
    val title: ContentModel? = null,
    val body: ContentModel? = null,
    val priority: ContentModel? = null
)