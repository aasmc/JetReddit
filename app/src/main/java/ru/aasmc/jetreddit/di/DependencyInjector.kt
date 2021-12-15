package ru.aasmc.jetreddit.di

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.aasmc.jetreddit.data.database.AppDatabase
import ru.aasmc.jetreddit.data.database.dbmapper.DbMapper
import ru.aasmc.jetreddit.data.database.dbmapper.DbMapperImpl
import ru.aasmc.jetreddit.data.repository.Repository
import ru.aasmc.jetreddit.data.repository.RepositoryImpl

/**
 * Provides dependencies across the app.
 */
@DelicateCoroutinesApi
class DependencyInjector(
    applicationContext: Context
) {
    val repository: Repository by lazy {
        provideRepository(database)
    }

    private val database: AppDatabase by lazy {
        provideDatabase(applicationContext)
    }

    private val dbMapper: DbMapper = DbMapperImpl()

    private fun provideDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    private fun provideRepository(database: AppDatabase): Repository {
        val postDao = database.postDao()
        return RepositoryImpl(postDao, dbMapper)
    }
}