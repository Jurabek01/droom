package io.mimsoft.advantege

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object AdvantageController {

    suspend fun getAll(
        serviceId: Int? = null,
    ): List<AdvantageModel?> {
        val query = "select * from advantage  where service_id = $serviceId"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            AdvantageModel(
                id = it.getInt("id"),
                serviceId = it.getInt("service_id"),
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng")
                ),
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng")
                )
            )
        }
    }

    suspend fun get(id: Int?): AdvantageModel? {
        val query = "select * from advantage where id = $id "

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            AdvantageModel(id = it.getInt("id"),
                serviceId = it.getInt("service_id"),
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng")
                ),
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng")
                )
            )
        }
    }

    suspend fun addAll(advantages: List<AdvantageModel?>?, serviceId: Int?): Boolean {
        if (advantages.isNullOrEmpty()) return false
        val stringList = arrayListOf<String?>()
        var query = "insert into advantage(service_id, body_uz, body_ru, body_eng, name_uz, name_ru, name_eng) values"
        advantages.forEach {
            if (stringList.isNotEmpty()) query += ",\n"
            query += "($serviceId, ?, ?, ?) "
            stringList.addAll(
                arrayListOf(
                    it?.body?.uz,
                    it?.body?.ru,
                    it?.body?.eng,
                    it?.name?.uz,
                    it?.name?.ru,
                    it?.name?.eng
                )
            )
        }
        DBManager.getConnection().sendPreparedStatementAwait(query, stringList)

        return true

    }

    suspend fun add(advantages: AdvantageModel?): Boolean {
        val query =
            "insert into advantage(service_id, " +
                    "body_uz, body_ru, body_eng," +
                    "name_uz, name_ru, name_eng ) \n" +
                    "values (${advantages?.serviceId}, ?, ?, ?, ?, ?, ?) "

        DBManager.getConnection().sendPreparedStatementAwait(query,
            arrayListOf(advantages?.body?.uz,
                advantages?.body?.ru,
                advantages?.body?.eng,
                advantages?.name?.uz,
                advantages?.name?.ru,
                advantages?.name?.eng)
        )

        return true
    }

    suspend fun edit(advantages: AdvantageModel?): Boolean {
        val query =
            "update advantage set service_id = ${advantages?.serviceId}, \n" + "body_uz = ?, body_ru = ?, body_eng = ?, \n" + "name_uz = ?, name_ru = ?, name_eng = ?  " + "where id = ${advantages?.id}"
        DBManager.getConnection().sendPreparedStatementAwait(query,
            arrayListOf(advantages?.body?.uz,
                advantages?.body?.ru,
                advantages?.body?.eng,
                advantages?.name?.uz,
                advantages?.name?.ru,
                advantages?.name?.eng))
        return true
    }

    suspend fun delete(advantage: AdvantageModel?): Boolean {
        val query =
            "delete from advantage where id = ${advantage?.id} " + if (advantage?.serviceId != null) "or service_id = ${advantage.serviceId} " else ""

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())
        return true
    }
}