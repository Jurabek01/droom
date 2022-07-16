package com.example.questions

import io.mimsoft.utils.ContentModel

data class QuestionsModel(
    val id: Int? = null,
    val priority: Int? = null,
    val title: ContentModel? = null,
    val answer: ContentModel? = null
)
