package io.mimsoft.header

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object HeaderController {

    suspend fun get(): HeaderModel?{
        val query = "select * from head"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            HeaderModel(
                title = ContentModel(
                    uz = it.getString("title_uz"),
                    ru = it.getString("title_ru"),
                    eng = it.getString("title_eng"),
                ),
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng"),
                )
            )
        }
    }

    suspend fun add(head: HeaderModel?): Boolean{
        val query = "insert into head (title_uz, title_ru, title_eng, body_uz, body_ru, body_eng) \n" +
                "values (?, ?, ?, ?, ?, ?)"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf(
            head?.title?.uz,
            head?.title?.ru,
            head?.title?.eng,
            head?.body?.uz,
            head?.body?.ru,
            head?.body?.eng
        ))

        return true
    }
}