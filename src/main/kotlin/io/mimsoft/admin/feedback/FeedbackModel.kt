package io.mimsoft.admin.feedback

import io.mimsoft.utils.ContentModel

data class FeedbackModel(
    val id: Int? = null,
    val priority: Int? = null,
    val name: ContentModel? = null,
    val body: ContentModel? = null,
    val image : String? = null
)
