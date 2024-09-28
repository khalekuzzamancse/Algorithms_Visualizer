package database.factory

import database.apis.GraphApi

expect class DatabaseFactory {
    fun createGraphApi():GraphApi

}