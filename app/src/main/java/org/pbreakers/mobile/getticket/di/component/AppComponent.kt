package org.pbreakers.mobile.getticket.di.component

import dagger.Component
import org.pbreakers.mobile.getticket.di.module.*
import org.pbreakers.mobile.getticket.model.entity.Agence
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetModule::class,
    RetrofitServiceModule::class,
    DatabaseModule::class
])
interface AppComponent {
    // Field injection


    // Constructor injection
    fun agenceDao(): Agence
}