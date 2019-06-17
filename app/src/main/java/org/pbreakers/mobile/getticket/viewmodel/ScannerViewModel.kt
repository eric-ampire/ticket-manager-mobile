package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Completable
import io.reactivex.Single
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.repository.BilletRepository
import javax.inject.Inject

class ScannerViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var billetRepository: BilletRepository

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun findBilletById(idBillet: Long): Single<Billet> {
        return billetRepository.findById(idBillet)
    }

    fun updateBillet(billet: Billet): Completable {
        // Todo: Implementation
        TODO()
    }
}