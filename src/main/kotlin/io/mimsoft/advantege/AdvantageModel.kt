package io.mimsoft.advantege

import io.mimsoft.utils.ContentModel

data class AdvantageModel(
    val id: Int? = null,
    val serviceId: Int? = null,
    val body: ContentModel? = null,
    val name: ContentModel? = null
)
