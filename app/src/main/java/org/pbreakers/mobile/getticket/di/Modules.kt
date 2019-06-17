package org.pbreakers.mobile.getticket.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.pbreakers.mobile.getticket.model.AppDatabase
import org.pbreakers.mobile.getticket.model.api.*
import org.pbreakers.mobile.getticket.model.dao.*
import org.pbreakers.mobile.getticket.model.repository.*
import org.pbreakers.mobile.getticket.util.Session
import org.pbreakers.mobile.getticket.viewmodel.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    fun provideAgenceApi(retrofit: Retrofit): AgenceApi = retrofit.create(AgenceApi::class.java)
    fun provideBilletApi(retrofit: Retrofit): BilletApi = retrofit.create(BilletApi::class.java)
    fun provideBusApi(retrofit: Retrofit): BusApi = retrofit.create(BusApi::class.java)
    fun provideEtatApi(retrofit: Retrofit): EtatApi = retrofit.create(EtatApi::class.java)
    fun provideLieuApi(retrofit: Retrofit): LieuApi = retrofit.create(LieuApi::class.java)
    fun providePointArretApi(retrofit: Retrofit): PointArretApi = retrofit.create(PointArretApi::class.java)
    fun provideRoleApi(retrofit: Retrofit): RoleApi = retrofit.create(RoleApi::class.java)
    fun provideTransitApi(retrofit: Retrofit): TransitApi = retrofit.create(TransitApi::class.java)
    fun provideVoyageApi(retrofit: Retrofit): VoyageApi = retrofit.create(VoyageApi::class.java)
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    single { provideAgenceApi(get()) }
    single { provideBilletApi(get()) }
    single { provideBusApi(get()) }
    single { provideEtatApi(get()) }
    single { provideLieuApi(get()) }
    single { providePointArretApi(get()) }
    single { provideRoleApi(get()) }
    single { provideTransitApi(get()) }
    single { provideVoyageApi(get()) }
    single { provideUserApi(get()) }
}

val appModule = module {

    fun provideSharedPreference(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
    fun provideSession(preferences: SharedPreferences, gson: Gson): Session {
        return Session(preferences, gson)
    }

    single { provideSession(get(), get()) }
    single { provideSharedPreference(androidApplication()) }
}

val netModule = module {

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }


    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
    single { provideHttpClient(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideArticleDao(database: AppDatabase): AgenceDao = database.agenceDao()
    fun provideUtilisateurDao(database: AppDatabase): UtilisateurDao = database.utilisateurDao()
    fun provideEtatDao(database: AppDatabase): EtatDao = database.etatDao()
    fun provideVoyageDao(database: AppDatabase): VoyageDao = database.voyageDao()
    fun provideLieuDao(database: AppDatabase): LieuDao = database.lieuDao()
    fun provideTransitDao(database: AppDatabase): TransitDao = database.transitDao()
    fun provideBilletDao(database: AppDatabase): BilletDao = database.billetDao()
    fun providePointArretDao(database: AppDatabase): PointArretDao = database.pointArretDao()
    fun provideBusDao(database: AppDatabase): BusDao = database.busDao()
    fun provideRoleDao(database: AppDatabase): RoleDao = database.roleDao()

    single { provideDatabase(androidApplication()) }
    single { provideArticleDao(get()) }
    single { provideUtilisateurDao(get()) }
    single { provideEtatDao(get()) }
    single { provideLieuDao(get()) }
    single { provideVoyageDao(get()) }
    single { provideTransitDao(get()) }
    single { provideBilletDao(get()) }
    single { providePointArretDao(get()) }
    single { provideBusDao(get()) }
    single { provideRoleDao(get()) }
}

val repositoryModule = module {
    single { AgenceRepository(get(), get()) }
    single { BilletRepository(get(), get()) }
    single { BusRepository(get(), get()) }
    single { EtatRepository(get(), get()) }
    single { LieuRepository(get(), get()) }
    single { PointArretRepository(get(), get()) }
    single { RoleRepository(get(), get()) }
    single { TransitRepository(get(), get()) }
    single { VoyageRepository(get(), get()) }
    single { UtilisateurRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { AuthViewModel() }
    viewModel { BilletViewModel() }
    viewModel { BusViewModel() }
    viewModel { BusDetailViewModel() }
    viewModel { DetailBilletViewModel() }
    viewModel { DetailVoyageViewModel() }
    viewModel { EnregViewModel() }
    viewModel { HomeViewModel() }
    viewModel { MainViewModel() }
    viewModel { ModifierBilletViewModel() }
    viewModel { ModifierBusViewModel() }
    viewModel { ScannerViewModel() }
    viewModel { SignalerAbusViewModel() }
    viewModel { ModifierVoyageViewModel() }
}