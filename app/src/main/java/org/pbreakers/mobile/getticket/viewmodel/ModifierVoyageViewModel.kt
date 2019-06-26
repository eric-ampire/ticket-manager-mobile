package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.model.entity.*

class ModifierVoyageViewModel : ViewModel(), KoinComponent {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    private val _role: MutableLiveData<List<Role>> by lazy {
        MutableLiveData<List<Role>>().also {
            findRole()
        }
    }

    private val _agences: MutableLiveData<List<Agence>> by lazy {
        MutableLiveData<List<Agence>>().also {
            findAgence()
        }
    }

    private val _bus: MutableLiveData<List<Bus>> by lazy {
        MutableLiveData<List<Bus>>().also {
            findBus()
        }
    }

    private val _etats: MutableLiveData<List<Etat>> by lazy {
        MutableLiveData<List<Etat>>().also {
            findEtat()
        }
    }

    private val _transits: MutableLiveData<List<Transit>> by lazy {
        MutableLiveData<List<Transit>>().also {
            findTransit()
        }
    }

    private val _lieux: MutableLiveData<List<Lieu>> by lazy {
        MutableLiveData<List<Lieu>>().also {
            findLieu()
        }
    }

    private val _voyages: MutableLiveData<List<Voyage>> by lazy {
        MutableLiveData<List<Voyage>>().also {
            findTravel()
        }
    }

    val role: LiveData<List<Role>>
        get() = _role

    val agences: LiveData<List<Agence>>
        get() = _agences

    val bus: LiveData<List<Bus>>
        get() = _bus

    val etats: LiveData<List<Etat>>
        get() = _etats

    val transits: LiveData<List<Transit>>
        get() = _transits

    val lieu: LiveData<List<Lieu>>
        get() = _lieux

    val voyages: LiveData<List<Voyage>>
        get() = _voyages

    private fun findRole() {
        db.collection("roles").addSnapshotListener { snapshot, exception ->
            if (exception != null && snapshot == null) return@addSnapshotListener

            val mRoles = snapshot!!.toObjects(Role::class.java)
            _role.postValue(mRoles)
        }
    }

    private fun findAgence() {
        db.collection("agences").addSnapshotListener { snapshot, exception ->
            if (exception != null && snapshot == null) return@addSnapshotListener

            val mItems = snapshot!!.toObjects(Agence::class.java)
            _agences.postValue(mItems)
        }
    }

    private fun findBus() {
        db.collection("bus").addSnapshotListener { snapshot, exception ->
            if (exception != null && snapshot == null) return@addSnapshotListener

            val mItems = snapshot!!.toObjects(Bus::class.java)
            _bus.postValue(mItems)
        }
    }

    private fun findEtat() {
        db.collection("etats").addSnapshotListener { snapshot, exception ->
            if (exception != null && snapshot == null) return@addSnapshotListener

            val mItems = snapshot!!.toObjects(Etat::class.java)
            _etats.postValue(mItems)
        }
    }

    private fun findTransit() {
        db.collection("transits").addSnapshotListener { snapshot, exception ->
            if (exception != null && snapshot == null) return@addSnapshotListener

            val mItems = snapshot!!.toObjects(Transit::class.java)
            _transits.postValue(mItems)
        }
    }

    private fun findLieu() {
        db.collection("lieux").addSnapshotListener { snapshot, exception ->
            if (exception != null && snapshot == null) return@addSnapshotListener

            val mItems = snapshot!!.toObjects(Lieu::class.java)
            _lieux.postValue(mItems)
        }
    }


    private fun findTravel() {
        db.collection("voyages").addSnapshotListener { snapshot, exception ->
            if (exception != null && snapshot == null) return@addSnapshotListener

            val mItems = snapshot!!.toObjects(Voyage::class.java)
            _voyages.postValue(mItems)
        }
    }


    fun update(voyage: Voyage): Task<Void> {
        return db.collection("voyages")
            .document(voyage.idVoyage)
            .set(voyage)
    }
}