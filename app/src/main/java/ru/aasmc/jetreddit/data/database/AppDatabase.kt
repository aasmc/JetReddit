package ru.aasmc.jetreddit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.aasmc.jetreddit.data.database.dao.PostDao
import ru.aasmc.jetreddit.data.database.model.PostDbModel

@Database(entities = [PostDbModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "jet-reddit-database"
    }

    abstract fun postDao(): PostDao
}