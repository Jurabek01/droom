package io.mimsoft.portfolio

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object PortfolioController {

    suspend fun getAll(
        serviceId: Int? = null,
        top: Boolean? = null,
    ): List<PortfolioModel?> {
        val query = "select * from portfolio  where not is_deleted " +
                (if (serviceId != null) " and service_id = $serviceId \n" else "") +
                (if (top != null) "and is_top = $top \n" else "") +
                "order by priority"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            PortfolioModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                title = ContentModel(
                    uz = it.getString("title_uz"),
                    ru = it.getString("title_ru"),
                    eng = it.getString("title_eng"),
                ),
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng"),
                ),
                image = it.getString("image"),
                serviceId = it.getInt("service_id"),
                top = it.getBoolean("is_top")
            )
        }
    }

    suspend fun get(id: Int?): PortfolioModel? {
        val query = "select * from portfolio where not is_deleted and id = $id"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            PortfolioModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                title = ContentModel(
                    uz = it.getString("title_uz"),
                    ru = it.getString("title_ru"),
                    eng = it.getString("title_eng"),
                ),
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng"),
                ),
                image = it.getString("image"),
                top = it.getBoolean("is_top")
            )
        }
    }

    suspend fun add(portfolio: PortfolioModel?): Boolean {
        val query = "insert into portfolio (priority, title_uz, title_ru, title_eng, \n" +
                "body_uz, body_ru, body_eng, image, service_id, is_top) values (${portfolio?.priority}, \n" +
                "?, ?, ?, ?, ?, ?, ?, ${portfolio?.serviceId},${portfolio?.top}) "

        DBManager.getConnection().sendPreparedStatementAwait(query,
            arrayListOf(
                portfolio?.title?.uz,
                portfolio?.title?.ru,
                portfolio?.title?.eng,
                portfolio?.body?.uz,
                portfolio?.body?.ru,
                portfolio?.body?.eng,
                portfolio?.image
            ))
        return true

    }


    suspend fun edit(portfolio: PortfolioModel?): Boolean {
        val query = "update portfolio set title_uz = ?, title_ru = ?, title_eng = ?, \n" +
                "body_uz = ?, body_ru = ?, body_eng = ?, image = ?, \n" +
                "priority = ${portfolio?.priority}, " +
                "service_id = ${portfolio?.serviceId}, " +
                "is_top = ${portfolio?.top} " +
                "where id = ${portfolio?.id} and not is_deleted"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf(
            portfolio?.title?.uz,
            portfolio?.title?.ru,
            portfolio?.title?.eng,
            portfolio?.body?.uz,
            portfolio?.body?.ru,
            portfolio?.body?.eng,
            portfolio?.image
        ))

        return true
    }

    suspend fun delete(portfolio: PortfolioModel?): Boolean {
        val query = "update portfolio set is_deleted = true where id = ${portfolio?.id}"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())

        return true
    }
}