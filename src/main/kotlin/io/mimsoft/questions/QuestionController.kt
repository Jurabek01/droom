package com.example.questions

import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait


object QuestionController{


        suspend fun getAll(): List<QuestionsModel> {
            val query = "select * from questions where not is_deleted order by priority"

            return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
                QuestionsModel(
                    id = it.getInt("id"),
                    priority = it.getInt("priority"),
                    title = ContentModel(
                        uz = it.getString("title_uz"),
                        ru = it.getString("title_ru"),
                        eng = it.getString("title_eng")
                    ),
                    answer = ContentModel(
                        uz = it.getString("answer_uz"),
                        ru = it.getString("answer_ru"),
                        eng = it.getString("answer_eng"),

                    )
                )
            }
        }

        suspend fun get(id: Int?): QuestionsModel?{
            val query = "select * from questions where  id = $id and not is_deleted"

            return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.getOrNull(0)?.let {
                QuestionsModel(
                    id = it.getInt("id"),
                    priority = it.getInt("priority"),
                    title = ContentModel(
                        uz = it.getString("title_uz"),
                        ru = it.getString("title_ru"),
                        eng = it.getString("title_eng")
                    ),
                    answer = ContentModel(
                        uz = it.getString("answer_uz"),
                        ru = it.getString("answer_ru"),
                        eng = it.getString("answer_eng")
                    )
                )
            }
        }

        suspend fun add(questions: QuestionsModel?): Boolean {
            val query = "insert into questions(priority, title_uz, title_ru, title_eng, answer_uz, answer_ru, answer_eng) values " +
                    "(${questions?.priority}, ?, ?, ?, ?, ?, ?)"
            DBManager.getConnection().sendPreparedStatementAwait(
                query,
                arrayListOf(
                    questions?.title?.uz,
                    questions?.title?.ru,
                    questions?.title?.eng,
                    questions?.answer?.uz,
                    questions?.answer?.ru,
                    questions?.answer?.eng
                )
            )
            return true

        }

        suspend fun edit(questions: QuestionsModel?): Boolean{
            val query = "update questions set \n" +
                    "priority = ${questions?.priority}, \n" +
                    "title_uz = ?, \n" +
                    "title_ru = ?, \n" +
                    "title_eng = ?, \n" +
                    "answer_uz = ?, \n" +
                    "answer_ru = ?, \n" +
                    "answer_eng = ? \n" +
                    "where id = ${questions?.id} and not is_deleted"
            DBManager.getConnection().sendPreparedStatementAwait(
                query, arrayListOf(
                    questions?.title?.uz,
                    questions?.title?.ru,
                    questions?.title?.eng,
                    questions?.answer?.uz,
                    questions?.answer?.ru,
                    questions?.answer?.eng
                )
            )
            return true
        }

        suspend fun delete(questions: QuestionsModel?): Boolean {
            val query = "update questions set is_deleted = true where id = ${questions?.id}"
            DBManager.getConnection().sendPreparedStatementAwait(query, ArrayList())
            return true
        }
}