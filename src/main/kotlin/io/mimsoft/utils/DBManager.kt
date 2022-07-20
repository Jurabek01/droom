package io.mimsoft.utils
import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import java.util.concurrent.TimeUnit

object DBManager {
    private val dbConnection by lazy {
        return@lazy PostgreSQLConnectionBuilder.createConnectionPool {
            username = "postgres"
            host = "109.68.212.187"
            port = 5432
            password = "123dreamarch321"
            database = "dreamarch"
            maxActiveConnections = 2
            maxIdleTime = TimeUnit.MINUTES.toMillis(1)
            maxPendingQueries = 100_000
            connectionValidationInterval = TimeUnit.SECONDS.toMillis(5)
        }.connect().get()
    }

    fun getConnection(): Connection = if (dbConnection.isConnected()) dbConnection else dbConnection.connect().get()
}
