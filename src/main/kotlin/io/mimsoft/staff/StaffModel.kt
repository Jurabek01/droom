package io.mimsoft.staff

import io.mimsoft.utils.ContentModel

data class StaffModel(
    val id: Int? = null,
    val priority: Int? = null,
    val name: ContentModel? = null,
    val job: ContentModel? = null,
    val image : String? = null
)