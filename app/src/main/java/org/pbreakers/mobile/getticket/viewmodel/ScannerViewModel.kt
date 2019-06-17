package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.repository.BilletRepository

class ScannerViewModel : ViewModel(), KoinComponent {

    private val billetRepository: BilletRepository by inject()

    fun findBilletById(idBillet: Long): Single<Billet> {
        return billetRepository.findById(idBillet)
    }

    fun updateBillet(billet: Billet): Completable {
        TODO()
    }
}