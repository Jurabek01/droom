package com.example.staff

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait
import io.mimsoft.staff.StaffModel

object StaffController {


    suspend fun getAll(): List<StaffModel?> {
        val query = "select * from staff where not is_deleted order by priority"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            StaffModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                image = it.getString("image"),
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng")
                ),
                job = ContentModel(
                    uz = it.getString("job_uz"),
                    ru = it.getString("job_ru"),
                    eng = it.getString("job_eng")
                )
            )
        }
    }

    suspend fun get(id: Int?): StaffModel? {
        val query = "select * from staff where id = $id and not is_deleted"

        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
            StaffModel(
                id = it.getInt("id"),
                priority = it.getInt("priority"),
                image = it.getString("image"),
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng")
                ),
                job = ContentModel(
                    uz = it.getString("job_uz"),
                    ru = it.getString("job_ru"),
                    eng = it.getString("job_eng")
                )
            )
        }
    }

    suspend fun add(staff: StaffModel?): Boolean {
        val query = "insert into staff(priority, image, name_uz, name_ru, name_eng, job_uz, job_ru, job_eng) values " +
                "(${staff?.priority}, ?, ?, ?, ?, ?, ?, ?)"
        DBManager.getConnection().sendPreparedStatementAwait(
            query,
            arrayListOf(
                staff?.image,
                staff?.name?.uz,
                staff?.name?.ru,
                staff?.name?.eng,
                staff?.job?.uz,
                staff?.job?.ru,
                staff?.job?.eng
            )
        )
        return true

    }

    suspend fun edit(staff: StaffModel?): Boolean {
        val query = "update staff set " +
                "priority = ${staff?.priority}, \n" +
                "image = ?, \n" +
                "name_uz = ?, \n" +
                "name_ru = ?, \n" +
                "name_eng = ?, \n" +
                "job_uz = ?, \n" +
                "job_ru = ?, \n" +
                "job_eng = ? \n" +
                "where id = ${staff?.id} and not is_deleted "
        DBManager.getConnection().sendPreparedStatementAwait(
            query,
            arrayListOf(
                staff?.image,
                staff?.name?.uz,
                staff?.name?.ru,
                staff?.name?.eng,
                staff?.job?.uz,
                staff?.job?.ru,
                staff?.job?.eng
            )
        )
        return true
    }


    suspend fun delete(staff: StaffModel?):Boolean{
        val query = "update staff set is_deleted = true where id = ${staff?.id}"
        DBManager.getConnection().sendPreparedStatementAwait(query,ArrayList())
        return true
    }


}