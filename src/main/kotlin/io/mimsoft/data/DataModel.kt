package io.mimsoft.data

import io.mimsoft.about.AboutModel
import io.mimsoft.contact.ContactModel
import io.mimsoft.portfolio.PortfolioModel
import io.mimsoft.service.ServiceModel
import io.mimsoft.utils.ContentModel

data class DataModel(
    val portfolios: List<PortfolioModel?>? = null,
    val services: List<ServiceModel?>? = null,
    val about: AboutModel? = null,
    val contact: ContactModel? = null,
    val aboutTitle : ContentModel? = null,
    val aboutBody : ContentModel? = null,
    val headerTitle : ContentModel? = null,
    val headerBody : ContentModel? = null
)