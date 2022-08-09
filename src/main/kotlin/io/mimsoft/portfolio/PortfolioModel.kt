package io.mimsoft.portfolio

import io.mimsoft.utils.ContentModel

data class PortfolioModel(
    val id: Int? = null,
    val priority: Int? = null,
    val title: ContentModel? = null,
    val body: ContentModel? = null,
    val image: String? = null,
    val serviceId : Int? = null,
    val top : Boolean? = null
)

 
