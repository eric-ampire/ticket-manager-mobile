package org.pbreakers.mobile.getticket.di.component

import dagger.Component
import org.pbreakers.mobile.getticket.di.module.*
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.view.activity.SplashActivity
import org.pbreakers.mobile.getticket.util.Session
import org.pbreakers.mobile.getticket.view.activity.MainActivity
import org.pbreakers.mobile.getticket.view.fragment.BusFragment
import org.pbreakers.mobile.getticket.view.fragment.EnregFragment
import org.pbreakers.mobile.getticket.viewmodel.*
import javax.inject.Singleton
import android.app.Application
import dagger.BindsInstance
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.view.fragment.DetailVoyageFragment


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
    fun inject(splashActivity: SplashActivity)
    fun inject(companion: Session)
    fun inject(mainActivity: MainActivity)
    fun inject(busViewModel: BusViewModel)
    fun inject(busFragment: BusFragment)
    fun inject(homeViewModel: HomeViewModel)
    fun inject(busDetailViewModel: BusDetailViewModel)
    fun inject(enregFragment: EnregFragment)
    fun inject(enregViewModel: EnregViewModel)
    fun inject(signalerAbusViewModel: SignalerAbusViewModel)
    fun inject(mainViewModel: MainViewModel)
    fun inject(billetViewModel: BilletViewModel)

    // Constructor injection
    fun utilisateurDao(): UtilisateurDao

    fun inject(modifierVoyageViewModel: ModifierVoyageViewModel)
    fun inject(detailVoyageViewModel: DetailVoyageViewModel)
    fun inject(detailVoyageFragment: DetailVoyageFragment)
    fun inject(detailBilletViewModel: DetailBilletViewModel)


}