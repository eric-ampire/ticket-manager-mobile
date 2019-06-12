package org.pbreakers.mobile.getticket.di.module

import dagger.Module
import dagger.Provides
import org.pbreakers.mobile.getticket.model.api.*
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideAgenceApi(retrofit: Retrofit): AgenceApi {
        return retrofit.create(AgenceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBilletApi(retrofit: Retrofit): BilletApi {
        return retrofit.create(BilletApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBusApi(retrofit: Retrofit): BusApi {
        return retrofit.create(BusApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEtatApi(retrofit: Retrofit): EtatApi {
        return retrofit.create(EtatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLieuApi(retrofit: Retrofit): LieuApi {
        return retrofit.create(LieuApi::class.java)
    }

    @Provides
    @Singleton
    fun providePointArretApi(retrofit: Retrofit): PointArretApi {
        return retrofit.create(PointArretApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoleApi(retrofit: Retrofit): RoleApi {
        return retrofit.create(RoleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransitApi(retrofit: Retrofit): TransitApi {
        return retrofit.create(TransitApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVoyageApi(retrofit: Retrofit): VoyageApi {
        return retrofit.create(VoyageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}