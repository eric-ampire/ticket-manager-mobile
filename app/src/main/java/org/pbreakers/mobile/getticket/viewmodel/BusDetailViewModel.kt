package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.pbreakers.mobile.getticket.app.App

class BusDetailViewModel(val app: Application) : AndroidViewModel(app) {

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun init() {

    }
}