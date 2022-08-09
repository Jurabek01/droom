package io.mimsoft.contact

import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object ContactController {

    suspend fun get(): ContactModel?{
        val query = "select * from contact "

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            ContactModel(
                number = it.getString("number"),
                address = it.getString("address"),
                lat = it.getDouble("lat"),
                long = it.getDouble("lang")
            )
        }
    }

    suspend fun add(contact: ContactModel?): Boolean{
        val query = "insert into contact (number, address, lat, lang) values (?, ?, ${contact?.lat}, ${contact?.long})"

        DBManager.getConnection().sendPreparedStatementAwait("delete from contact where true", arrayListOf())

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf(
            contact?.number,
            contact?.address
        ))

        return true
    }
}