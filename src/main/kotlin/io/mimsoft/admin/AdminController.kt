package io.mimsoft.admin

import io.mimsoft.session.SessionController
import io.mimsoft.session.SessionModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.JWTConfig
import io.mimsoft.utils.sendPreparedStatementAwait
import java.util.*
import kotlin.collections.ArrayList

object AdminController {
    suspend fun getAdmins(): List<AdminModel?> {

        val query = "select * from admin"

        return DBManager.getConnection().sendPreparedStatementAwait(
            query, ArrayList()
        ).rows.map {

            AdminModel(
                id = it.getInt("id"),
                username = it.getString("username"),
                password = it.getString("password")
            )

        }


    }

    suspend fun getAdmin(id: Int? = null, username: String? = null): AdminModel? {
        val arrayList = arrayListOf<String?>()
        val query = "select * from admin where " +
                if (id != null)
                    "id = $id"
                else {
                    arrayList.add(username)
                    "username = ?"
                }


        return DBManager.getConnection().sendPreparedStatementAwait(
            query, arrayList
        ).rows.getOrNull(0)?.let {

            AdminModel(
                id = it.getInt("id"),
                username = it.getString("username"),
                password = it.getString("password")
            )

        }


    }

    suspend fun checkAdmin(id: Int?, username: String?): AdminModel? {
        val query = "select * from admin where id = $id and username = ?"

        return DBManager.getConnection().sendPreparedStatementAwait(
            query, arrayListOf(username)
        ).rows.getOrNull(0)?.let {

            AdminModel(
                id = it.getInt("id"),
                username = it.getString("username"),
                password = it.getString("password")
            )

        }
    }


    suspend fun authAdmin(admin: AdminModel?): AdminModel? {

        val query = "select * from admin where password = ? and username = ?"

        return DBManager.getConnection().sendPreparedStatementAwait(
            query, arrayListOf(admin?.password, admin?.username)
        ).rows.getOrNull(0)?.let {

            val username = it.getString("username")
            val sessionUuid = UUID.randomUUID().toString() + "-" + it.getInt("id")

            SessionController.addSession(
                SessionModel(
                    adminId = it.getInt("id"),
                    uuid = admin?.uuid,
                    sessionUuid = sessionUuid
                )
            )
            AdminModel(
                id = it.getInt("id"),
                username = it.getString("username"),
                password = it.getString("password"),
                token = JWTConfig.makeTokenAdmin(it.getInt("id"), username, sessionUuid)
            )
        }


    }


//    suspend fun addAdmin(admin: AdminModel?): AdminModel? {
//        if (admin == null) return null
//        val check = getAdmin(username = admin.username) == null
//        return if (check) {
//            val query = "insert into admin(username, password) values(" +
//                    "?, ?) returning id"
//            val id: Int? = DBManager.getConnection().sendPreparedStatementAwait(
//                query, arrayListOf(admin.username, admin.password)
//            ).rows.getOrNull(0)?.getInt("id")
//
////            val token = JWTConfig.makeTokenAdmin(id, admin.username, admin.uuid)
//
//            return admin.copy(token = token)
//        } else {
//            null
//        }
//
//    }
//
//    suspend fun changePassword(admin: AdminModel?): Boolean {
//        val query = "update admin set password = ? where id = ${admin?.id}"
//
//        DBManager.getConnection().sendPreparedStatementAwait(
//            query, arrayListOf(admin?.password)
//        )
//        return true
//    }


    suspend fun editAdmin(admin: AdminModel?): Boolean {
        val query = "update admin set password = ? , username = ? where id = ${admin?.id}"
        DBManager.getConnection().sendPreparedStatementAwait(
            query, arrayListOf(admin?.password, admin?.username)
        )
        return true
    }


    suspend fun deleteAdmin(admin: AdminModel): Boolean {

        DBManager.getConnection().sendPreparedStatementAwait(
            "delete from admin where id = ${admin.id}", arrayListOf()
        )
        return true
    }
}