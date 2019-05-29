package org.pbreakers.mobile.getticket.di.component

import dagger.Component
import org.pbreakers.mobile.getticket.di.module.*
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.viewmodel.AuthViewModel
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
    fun inject(authViewModel: AuthViewModel)

    // Constructor injection
    fun utilisateurDao(): UtilisateurDao
}