package io.mimsoft.adventures

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object AdventuresController {

    suspend fun getAll(
        serviceId: Int? = null,
    ): List<AdventuresModel?> {
        val query = "select * from adventure " +
                "where service_id = $serviceId"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            AdventuresModel(
                id = it.getInt("id"),
                serviceId = it.getInt("service_id"),
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng")
                )
            )
        }
    }

    suspend fun get(id: Int?): AdventuresModel? {
        val query = "select * from adventure where id = $id "

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            AdventuresModel(
                id = it.getInt("id"),
                serviceId = it.getInt("service_id"),
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng")
                )
            )
        }
    }

    suspend fun addAll(adventures: List<AdventuresModel?>?, serviceId: Int?): Boolean {
        if (adventures.isNullOrEmpty())
            return false
        val stringList = arrayListOf<String?>()
        var query = "insert into adventure(service_id, body_uz, body_ru, body_eng) values"
        adventures.forEach {
            if(stringList.isNotEmpty())
                query += ",\n"
            query += "($serviceId, ?, ?, ?) "
            stringList.addAll(arrayListOf( it?.body?.uz, it?.body?.ru, it?.body?.eng))
        }
        DBManager.getConnection().sendPreparedStatementAwait(
            query, stringList
        )

        return true

    }

    suspend fun add(adventures: AdventuresModel?): Boolean {
        val query = "insert into adventure(service_id, body_uz, body_ru, body_eng) \n" +
                "values (${adventures?.serviceId}, ?, ?, ?) "

        DBManager.getConnection().sendPreparedStatementAwait(
            query, arrayListOf(
                adventures?.body?.uz,
                adventures?.body?.ru,
                adventures?.body?.eng
            )
        )

        return true
    }

    suspend fun edit(adventures: AdventuresModel?): Boolean {
        val query = "update adventure set service_id = ${adventures?.serviceId}, \n" +
                "body_uz = ?, body_ru = ?, body_eng = ? where id = ${adventures?.serviceId}"
        DBManager.getConnection().sendPreparedStatementAwait(
            query, arrayListOf(
                adventures?.body?.uz,
                adventures?.body?.ru,
                adventures?.body?.eng
            )
        )
        return true
    }

    suspend fun delete(adventure: AdventuresModel?): Boolean {
        val query = "delete from adventure where id = ${adventure?.id} " +
                if (adventure?.serviceId != null) "or service_id = ${adventure.serviceId} " else ""

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())
        return true
    }
}