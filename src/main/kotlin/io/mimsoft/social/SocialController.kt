package io.mimsoft.social

import com.example.questions.social.SocialModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object SocialController {

    suspend fun getAll(): List<SocialModel> {
        val query = "select * from social  order by priority"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            SocialModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                url = it.getString("url"),
                icon = it.getString("icon")
            )
        }
    }

    suspend fun get(id: Int?): SocialModel?{
        val query = "select * from social where id = $id"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            SocialModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                url = it.getString("url"),
                icon = it.getString("icon")
            )
        }
    }

    suspend fun add(social: SocialModel): Boolean{
        val query = "insert into social (priority, url, icon) VALUES (${social.priority},?,?)\n"

        println(query)
        DBManager.getConnection().sendPreparedStatementAwait(
            query,
            arrayListOf(
                social.url,
                social.icon
            )
        )
        return true
    }

    suspend fun edit(social: SocialModel): Boolean{
        val query = "update social set \n" +
                "priority = ${social.priority}, \n" +
                "url = ?, \n" +
                "icon = ? \n" +
                "where id = ${social.id}"
        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf(
            social.url,
            social.icon
        ))
        return true
    }

    suspend fun delete(social: SocialModel): Boolean{
        val query = "delete  from social where id = ${social.id}"
        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())
        return true
    }
}