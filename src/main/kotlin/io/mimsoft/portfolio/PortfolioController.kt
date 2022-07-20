package io.mimsoft.portfolio

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object PortfolioController {

    suspend fun getAll(): List<PortfolioModel?>{
        val query = "select * from portfolio order by priority"

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
                image = it.getString("image")
            )
        }
    }

    suspend fun get(id: Int?): PortfolioModel?{
        val query = "select * from portfolio where id = $id"

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
                image = it.getString("image")
            )
        }
    }

    suspend fun add(portfolio: PortfolioModel?): Boolean{
        val query = "insert into portfolio (priority, title_uz, title_ru, title_eng, \n" +
                "body_uz, body_ru, body_eng, image) values (${portfolio?.priority}, \n" +
                "?, ?, ?, ?, ?, ?, ?)"
        println(query)

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

    suspend fun edit(portfolio: PortfolioModel?): Boolean{
        val query = "update portfolio set title_uz = ?, title_ru = ?, title_eng = ?, \n" +
                "body_uz = ?, body_ru = ?, body_eng = ?, image = ?, \n" +
                "priority = ${portfolio?.priority} where id = ${portfolio?.id} and not is_deleted"

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

    suspend fun delete(portfolio: PortfolioModel?): Boolean{
        val query = "update portfolio set is_deleted = true where id = ${portfolio?.id}"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())

        return true
    }
}