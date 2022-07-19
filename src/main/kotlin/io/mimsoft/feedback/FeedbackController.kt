package io.mimsoft.feedback

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object FeedbackController {

    suspend fun getAll(): List<FeedbackModel?> {
        val query = " select * from feedback order by priority"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            FeedbackModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng"),
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

    suspend fun get(id: Int?): FeedbackModel?{
        val query = "select * from feedback where id = $id"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            FeedbackModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng"),
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

    suspend fun add(feedback: FeedbackModel?): Boolean{
        val query = "insert into feedback " +
                "(priority, name_uz, name_ru, name_eng, \n " +
                "body_uz, body_ru, body_eng, " +
                "image) values (${feedback?.priority} ,\n" +
                "?, ?, ?, ?, ?, ?, ?)"

        DBManager.getConnection().sendPreparedStatementAwait(query,
        arrayListOf(
            feedback?.name?.uz,
            feedback?.name?.ru,
            feedback?.name?.eng,
            feedback?.body?.uz,
            feedback?.body?.ru,
            feedback?.body?.eng,
            feedback?.image
        ))

        return true
    }

    suspend fun edit(feedback: FeedbackModel?): Boolean{
        val query = "update feedback set priority = ${feedback?.priority}, name_uz = ?, name_ru = ?, name_eng = ?, \n" +
                "body_uz = ?, body_ru = ?, body_eng = ?, image = ? where id = ${feedback?.id}"

        DBManager.getConnection().sendPreparedStatementAwait(query,
        arrayListOf(
            feedback?.name?.uz,
            feedback?.name?.ru,
            feedback?.name?.eng,
            feedback?.body?.uz,
            feedback?.body?.ru,
            feedback?.body?.eng,
            feedback?.image
        ))

        return true
    }

    suspend fun delete(feedback: FeedbackModel?): Boolean{
        val query = "update feedback set is_deleted = true where id = ${feedback?.id}"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())
        return true
    }
}
