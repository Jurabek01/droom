package io.mimsoft.data

import io.mimsoft.about.AboutController
import io.mimsoft.contact.ContactController
import io.mimsoft.portfolio.PortfolioController
import io.mimsoft.service.ServiceController
import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object DataController {
    suspend fun get(): DataModel? {
        val query = "select * from data "
        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            DataModel(
                portfolios = PortfolioController.getAll(top = true),
                services = ServiceController.getAllMainPage(),
                about = AboutController.get(),
                contact = ContactController.get(),
                aboutTitle = ContentModel(
                    uz = it.getString("about_title_uz"),
                    ru = it.getString("about_title_ru"),
                    eng = it.getString("about_title_eng")
                ),
                aboutBody = ContentModel(
                    uz = it.getString("about_body_uz"),
                    ru = it.getString("about_body_ru"),
                    eng = it.getString("about_body_eng")
                ),
                headerBody = ContentModel(
                    uz = it.getString("header_body_uz"),
                    ru = it.getString("header_body_ru"),
                    eng = it.getString("header_body_eng")
                ),
                headerTitle = ContentModel(
                    uz = it.getString("header_title_uz"),
                    ru = it.getString("header_title_ru"),
                    eng = it.getString("header_title_eng")
                )
            )
        }
    }

    suspend fun edit(data: DataModel?): Boolean {
        val query = "update table data set" +
                "about_title_uz = ?, about_title_ru = ?, about_title_eng = ? ,\n" +
                "about_body_uz = ?, about_body_ru = ?, about_body_eng = ? ,\n" +
                "header_title_uz = ?, header_title_ru = ?, header_title_eng = ? ,\n" +
                "header_body_uz = ?, header_body_ru = ?, header_body_eng = ? where true"

        DBManager.getConnection().sendPreparedStatementAwait(query,
            arrayListOf(
                data?.aboutTitle?.uz,
                data?.aboutTitle?.ru,
                data?.aboutTitle?.eng,
                data?.aboutBody?.uz,
                data?.aboutBody?.ru,
                data?.aboutBody?.eng,
                data?.headerTitle?.uz,
                data?.headerTitle?.ru,
                data?.headerTitle?.eng,
                data?.headerBody?.uz,
                data?.headerBody?.ru,
                data?.headerBody?.eng,
                ))

        return true

    }
}