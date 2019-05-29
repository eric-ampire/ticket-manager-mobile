package org.pbreakers.mobile.getticket.app

import android.app.Application
import androidx.multidex.MultiDex
import org.pbreakers.mobile.getticket.di.component.AppComponent
import org.pbreakers.mobile.getticket.di.component.DaggerAppComponent
import org.pbreakers.mobile.getticket.di.module.AppModule
import org.pbreakers.mobile.getticket.di.module.NetModule

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule("http://192.168.43.252/"))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}