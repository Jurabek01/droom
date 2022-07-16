package io.mimsoft.utils


import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.QueryResult
import kotlinx.coroutines.future.await

suspend fun Connection.sendPreparedStatementAwait(query: String, values: ArrayList<String?>): QueryResult =
    sendPreparedStatement(query, values).await()