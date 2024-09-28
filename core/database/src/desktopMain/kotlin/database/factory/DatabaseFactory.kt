@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package database.factory

import database.apis.GraphApi
import database.getDatabase

actual class DatabaseFactory {
    actual fun createGraphApi(): GraphApi {
        return GraphApiImpl(
            graphDao = getDatabase().graphDao()
        )
    }
}