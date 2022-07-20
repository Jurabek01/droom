package io.mimsoft.session

import io.mimsoft.session.SessionController.addSession
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait
import kotlinx.coroutines.InternalCoroutinesApi



@OptIn(InternalCoroutinesApi::class)
object SessionController {

    suspend fun addSession(sessionModel: SessionModel?=  null): Boolean{
        val query = "with upsert as (\n" +
                "    update session\n" +
                "        set session_uuid = ?, is_expired = false, expired_time = ${System.currentTimeMillis()}\n" +
                "        where (admin_id = ${sessionModel?.adminId}) and uuid = ? returning *)\n" +
                "insert\n" +
                "into session (uuid, session_uuid, is_expired, expired_time, created_at, admin_id)\n" +
                "select ?, ?, false, ${System.currentTimeMillis()}, ${System.currentTimeMillis()}, ${sessionModel?.adminId}\n" +
                "where not exists(select * from upsert);"

        DBManager.getConnection().sendPreparedStatementAwait(
            query,
            arrayListOf(
                sessionModel?.sessionUuid,
                sessionModel?.uuid,
                sessionModel?.uuid,
                sessionModel?.sessionUuid
            )
        )


        return true
    }

    suspend fun checkSession(sessionModel: SessionModel?): SessionModel? {
        var query = "select *\n" +
                "from session\n" +
                "where session_uuid = '${sessionModel?.sessionUuid}'\n"

        if (sessionModel?.adminId != null) {
            query += " and admin_id = ${sessionModel.adminId} "
        }

        return DBManager.getConnection().sendPreparedStatementAwait(
            query,
            arrayListOf()
        ).rows.getOrNull(0)?.let {


            SessionModel(
                id = it.getLong("id"),
                uuid = it.getString("uuid"),
                sessionUuid = it.getString("session_uuid"),
                adminId = it.getInt("admin_id"),
                isExpired = it.getBoolean("is_expired"),
                expiredTime = it.getLong("expired_time"),
                createdAt = it.getLong("created_at")
            )

        }
    }

    suspend fun getSession(sessionModel: SessionModel): SessionModel? {
        return DBManager.getConnection()
            .sendPreparedStatementAwait("select * from session", arrayListOf()).rows.getOrNull(0)?.let {

                SessionModel(
                    id = it.getLong("id"),
                    uuid = it.getString("uuid"),
                    sessionUuid = it.getString("session_uuid"),
                    adminId = it.getInt("admin_id"),
                    isExpired = it.getBoolean("is_expired"),
                    expiredTime = it.getLong("expired_time"),
                    createdAt = it.getLong("created_at")
                )

            }


    }

    suspend fun getSessions(): List<SessionModel?> {

        return DBManager.getConnection()
            .sendPreparedStatementAwait("select * from session", arrayListOf()).rows.map {

                SessionModel(
                    id = it.getLong("id"),
                    uuid = it.getString("uuid"),
                    sessionUuid = it.getString("session_uuid"),
                    adminId = it.getInt("admin_id"),
                    isExpired = it.getBoolean("is_expired"),
                    expiredTime = it.getLong("expired_time"),
                    createdAt = it.getLong("created_at")
                )

            }
    }

    suspend fun deleteSession(sessionModel: SessionModel): Boolean {

        DBManager.getConnection()
            .sendPreparedStatementAwait(
                "delete from session where (admin_id = ${sessionModel.adminId}) and session_uuid = ?",
                arrayListOf(sessionModel.sessionUuid)
            )
        return true
    }

    suspend fun editSession(sessionModel: SessionModel): Boolean {

        DBManager.getConnection().sendPreparedStatementAwait(
            "update session set is_expired = ${sessionModel.isExpired} " +
                    "where (admin_id = ${sessionModel.adminId}) " +
                    "and session_uuid = ?",
            arrayListOf(sessionModel.sessionUuid)
        )
        return true
    }
}
