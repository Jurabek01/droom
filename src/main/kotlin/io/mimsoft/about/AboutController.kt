package io.mimsoft.about

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object AboutController {

    suspend fun get(): AboutModel?{
        val query = "select * from about "
        return DBManager.getConnection().sendPreparedStatementAwait(query,ArrayList()).rows.getOrNull(0)?.let{
            AboutModel(
                body = ContentModel(
                    uz = it.getString("body_uz"),
                    ru = it.getString("body_ru"),
                    eng = it.getString("body_eng"),
                ),
                number1 = it.getInt("number1"),
                text1 = ContentModel(
                    uz = it.getString("text1_uz"),
                    ru = it.getString("text1_ru"),
                    eng = it.getString("text1_end"),
                ),
                number2 =  it.getInt("number2"),
                text2 = ContentModel(
                    uz = it.getString("text2_uz"),
                    ru = it.getString("text2_ru"),
                    eng = it.getString("text2_eng"),
                ),
                number3 = it.getInt("number3"),
                text3 = ContentModel(
                    uz = it.getString("text3_uz"),
                    ru = it.getString("text3_ru"),
                    eng = it.getString("text3_eng"),
                )
            )
        }
    }

    suspend fun add(about: AboutModel?): Boolean{
        val query = "insert into about (body_uz, body_ru, body_eng,\n" +
                "number1, text1_uz, text1_ru, text1_eng, \n" +
                "number2, text2_uz, text2_ru, text2_eng, \n" +
                "number3, text3_uz, text3_ru, text3_eng) \n" +
                "values (?, ?, ?, " +
                "${about?.number1}, ?, ?, ?, " +
                "${about?.number2}, ?, ?, ?, " +
                "${about?.number3}, ?, ?, ? )\n"
        DBManager.getConnection().sendPreparedStatementAwait("delete from about where true" ,ArrayList())

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf(
            about?.body?.uz,
            about?.body?.ru,
            about?.body?.eng,
            about?.text1?.uz,
            about?.text1?.ru,
            about?.text1?.eng,
            about?.text2?.uz,
            about?.text2?.ru,
            about?.text2?.eng,
            about?.text3?.uz,
            about?.text3?.ru,
            about?.text3?.eng
        ))

        return true
    }
}