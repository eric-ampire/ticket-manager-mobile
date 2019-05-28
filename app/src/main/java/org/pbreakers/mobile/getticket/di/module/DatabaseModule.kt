package org.pbreakers.mobile.getticket.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.pbreakers.mobile.getticket.model.AppDatabase
import org.pbreakers.mobile.getticket.model.dao.AgentDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: AppDatabase): AgentDao {
        return database.agenceDao()
    }
}