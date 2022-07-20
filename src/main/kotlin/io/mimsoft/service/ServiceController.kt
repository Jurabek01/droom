package io.mimsoft.service

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object ServiceController {

    suspend fun getAll(): List<ServiceModel>{
        val query = "select * from service order by priority"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            ServiceModel(
                id = it.getInt("id"),
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
                priority = it.getInt("priority")
            )
        }
    }

    suspend fun get(id: Int?): ServiceModel?{
        val query = "select * from service where id = $id"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            ServiceModel(
                id = it.getInt("id"),
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
                priority = it.getInt("priority")
            )
        }
    }

    suspend fun add(service: ServiceModel?): Boolean{
        val query = "insert into service (title_uz, title_ru, title_eng, \n" +
                "body_uz, body_ru, body_eng, priority) values \n" +
                "(?, ?, ?, ?, ?, ?, ${service?.priority})"

        DBManager.getConnection().sendPreparedStatementAwait(query,
            arrayListOf(
                service?.title?.uz,
                service?.title?.ru,
                service?.title?.eng,
                service?.body?.uz,
                service?.body?.ru,
                service?.body?.eng
            )
        )

        return true
    }

    suspend fun edit(service: ServiceModel?): Boolean{
        val query = "update service set title_uz = ?, title_ru = ?, title_eng = ?, \n" +
                "body_uz = ?, body_ru = ?, body_eng = ?, priority = ${service?.priority} where id = ${service?.id} and not is_deleted"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf(
            service?.title?.uz,
            service?.title?.ru,
            service?.title?.eng,
            service?.body?.uz,
            service?.body?.ru,
            service?.body?.eng
        ))

        return true
    }

    suspend fun delete(service: ServiceModel?): Boolean{
        val query = "update service set is_deleted = true where id = ${service?.id}"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())
        return true
    }
}