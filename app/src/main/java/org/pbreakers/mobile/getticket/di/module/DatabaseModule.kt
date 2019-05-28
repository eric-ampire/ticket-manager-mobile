package org.pbreakers.mobile.getticket.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.pbreakers.mobile.getticket.model.AppDatabase
import org.pbreakers.mobile.getticket.model.dao.*
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
    fun provideArticleDao(database: AppDatabase): AgenceDao {
        return database.agenceDao()
    }

    @Provides
    @Singleton
    fun provideUtilisateurDao(database: AppDatabase): UtilisateurDao {
        return database.utilisateurDao()
    }

    @Provides
    @Singleton
    fun provideEtatDao(database: AppDatabase): EtatDao {
        return database.etatDao()
    }

    @Provides
    @Singleton
    fun provideVoyageDao(database: AppDatabase): VoyageDao {
        return database.voyageDao()
    }

    @Provides
    @Singleton
    fun provideLieuDao(database: AppDatabase): LieuDao {
        return database.lieuDao()
    }

    @Provides
    @Singleton
    fun provideTransitDao(database: AppDatabase): TransitDao {
        return database.transitDao()
    }

    @Provides
    @Singleton
    fun provideBilletDao(database: AppDatabase): BilletDao {
        return database.billetDao()
    }

    @Provides
    @Singleton
    fun providePointArretDao(database: AppDatabase): PointArretDao {
        return database.pointArretDao()
    }

    @Provides
    @Singleton
    fun provideBusDao(database: AppDatabase): BusDao {
        return database.busDao()
    }

    @Provides
    @Singleton
    fun provideRoleDao(database: AppDatabase): RoleDao {
        return database.roleDao()
    }
}