package io.mimsoft.utils
import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import java.util.concurrent.TimeUnit

object DBManager {
    private val dbConnection by lazy {
        return@lazy PostgreSQLConnectionBuilder.createConnectionPool {
            username = "postgres"
            host = "89.223.124.220"
            port = 5432
            password = "!#@mimsoft!#@"
            database = "dreamarch"
            maxActiveConnections = 5
            maxIdleTime = TimeUnit.MINUTES.toMillis(1)
            maxPendingQueries = 100_000
            connectionValidationInterval = TimeUnit.SECONDS.toMillis(5)
        }.connect().get()
    }

    fun getConnection(): Connection = if (dbConnection.isConnected()) dbConnection else dbConnection.connect().get()
}
