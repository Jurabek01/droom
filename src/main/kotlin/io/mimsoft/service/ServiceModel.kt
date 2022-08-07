package io.mimsoft.service

import io.mimsoft.advantege.AdvantageModel
import io.mimsoft.portfolio.PortfolioModel
import io.mimsoft.utils.ContentModel

data class ServiceModel(
    val id: Int? = null,
    val title: ContentModel? = null,
    val body: ContentModel? = null,
    val name : ContentModel? = null,
    val hint : ContentModel? = null,
    val header : ContentModel? = null,
    val priority: Int? = null,
    val portfolios : List<PortfolioModel?>? = null,
    val advantages : List<AdvantageModel?>? = null,
    val image : String? = null
)