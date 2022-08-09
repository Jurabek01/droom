package io.mimsoft.service

import io.mimsoft.advantege.AdvantageController
import io.mimsoft.advantege.AdvantageModel
import io.mimsoft.portfolio.PortfolioController
import io.mimsoft.utils.ContentModel
import io.mimsoft.utils.DBManager
import io.mimsoft.utils.sendPreparedStatementAwait

object ServiceController {

    suspend fun getAll(): List<ServiceModel?> {
        val query = "select * from service where not is_deleted order by priority "

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
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng")
                ),
                header = ContentModel(
                    uz = it.getString("header_uz"),
                    ru = it.getString("header_ru"),
                    eng = it.getString("header_eng")
                ),
                hint = ContentModel(
                    uz = it.getString("hint_uz"),
                    ru = it.getString("hint_ru"),
                    eng = it.getString("hint_eng"),
                ),
                priority = it.getInt("priority"),
                portfolios = PortfolioController.getAll(serviceId = it.getInt("id")),
                advantages = AdvantageController.getAll(serviceId = it.getInt("id")),
                image = it.getString("image")
            )
        }
    }

    suspend fun getAllMainPage(): List<ServiceModel?> {
        val query = "select id , image, " +
                "title_uz, title_ru, title_eng, " +
                "name_uz, name_ru, name_eng " +
                "from service " +
                "where not is_deleted order by priority"
        return DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf()).rows.map {
            ServiceModel(
                id = it.getInt("id"),
                title = ContentModel(
                    uz = it.getString("title_uz"),
                    ru = it.getString("title_ru"),
                    eng = it.getString("title_eng"),
                ),
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng")
                ),
                image = it.getString("image")
            )
        }
    }

    suspend fun get(id: Int?): ServiceModel? {
        val query = "select * from service where id = $id and not is_deleted"

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
                name = ContentModel(
                    uz = it.getString("name_uz"),
                    ru = it.getString("name_ru"),
                    eng = it.getString("name_eng")
                ),
                header = ContentModel(
                    uz = it.getString("header_uz"),
                    ru = it.getString("header_ru"),
                    eng = it.getString("header_eng")
                ),
                hint = ContentModel(
                    uz = it.getString("hint_uz"),
                    ru = it.getString("hint_ru"),
                    eng = it.getString("hint_eng"),
                ),
                priority = it.getInt("priority"),
                advantages = AdvantageController.getAll(serviceId = id),
                portfolios = PortfolioController.getAll(serviceId = id),
                image = it.getString("image")
            )
        }
    }

    suspend fun add(service: ServiceModel?): Int? {
        val query = "insert into service " +
                "(title_uz, title_ru, title_eng, \n" +
                "body_uz, body_ru, body_eng, \n" +
                "name_uz, name_ru, name_eng, \n" +
                "hint_uz, hint_ru, hint_eng, \n" +
                "header_uz, header_ru, header_eng, \n" +
                "priority, image) values \n" +
                "(?, ?, ?, ?, ?, ?, ${service?.priority}, ?) returning id"

        val id = DBManager.getConnection().sendPreparedStatementAwait(query,
            arrayListOf(
                service?.title?.uz,
                service?.title?.ru,
                service?.title?.eng,
                service?.body?.uz,
                service?.body?.ru,
                service?.body?.eng,
                service?.name?.uz,
                service?.name?.ru,
                service?.name?.eng,
                service?.hint?.uz,
                service?.hint?.ru,
                service?.hint?.eng,
                service?.header?.uz,
                service?.header?.ru,
                service?.header?.eng,
                service?.image
            )
        ).rows.getOrNull(0)?.getInt("id")
        AdvantageController.addAll(advantages = service?.advantages, serviceId = id)
        return id
    }

    suspend fun edit(service: ServiceModel?): Boolean {
        val query = "update service set " +
                "title_uz = ?, title_ru = ?, title_eng = ?, \n" +
                "body_uz = ?, body_ru = ?, body_eng = ?, " +
                "name_uz = ?, name_ru = ?, name_eng = ?, " +
                "hint_uz = ?, hint_ru = ?, hint_eng = ?, " +
                "header_uz = ?, header_ru = ?, header_eng = ?, " +
                "priority = ${service?.priority} , image = ? where id = ${service?.id} and not is_deleted"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf(
            service?.title?.uz,
            service?.title?.ru,
            service?.title?.eng,
            service?.body?.uz,
            service?.body?.ru,
            service?.body?.eng,
            service?.name?.uz,
            service?.name?.ru,
            service?.name?.eng,
            service?.hint?.uz,
            service?.hint?.ru,
            service?.hint?.eng,
            service?.header?.uz,
            service?.header?.ru,
            service?.header?.eng,
            service?.image
        ))
        AdvantageController.delete(AdvantageModel(serviceId = service?.id))
        AdvantageController.addAll(service?.advantages, serviceId = service?.id)
        return true
    }

    suspend fun delete(service: ServiceModel?): Boolean {
        val query = "update service set is_deleted = true where id = ${service?.id}"

        DBManager.getConnection().sendPreparedStatementAwait(query, arrayListOf())
        return true
    }
}