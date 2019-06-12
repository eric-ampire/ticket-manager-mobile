package org.pbreakers.mobile.getticket.di.module

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.util.Session
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideSession(preferences: SharedPreferences, gson: Gson): Session {
        return Session(preferences, gson)
    }
}