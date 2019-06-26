package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import org.pbreakers.mobile.getticket.model.entity.*

class EnregViewModel : ViewModel() {

    private val db by lazy {
        FirebaseFirestore.getInstance()
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
        get() = _lieu

    val voyages: LiveData<List<Voyage>>
        get() = _voyages


    private val _role     = MutableLiveData<List<Role>>().apply { value = arrayListOf() }
    private val _agences  = MutableLiveData<List<Agence>>().apply { value = arrayListOf() }
    private val _bus      = MutableLiveData<List<Bus>>().apply { value = arrayListOf() }
    private val _etats    = MutableLiveData<List<Etat>>().apply { value = arrayListOf() }
    private val _transits = MutableLiveData<List<Transit>>().apply { value = arrayListOf() }
    private val _lieu     = MutableLiveData<List<Lieu>>().apply { value = arrayListOf() }
    private val _voyages  = MutableLiveData<List<Voyage>>().apply { value = arrayListOf() }

    init {

        db.collection("roles").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _role.postValue(snapshot.toObjects(Role::class.java))
        }

        db.collection("agences").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _agences.postValue(snapshot.toObjects(Agence::class.java))
        }

        db.collection("bus").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _bus.postValue(snapshot.toObjects(Bus::class.java))
        }

        db.collection("etats").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _etats.postValue(snapshot.toObjects(Etat::class.java))
        }

        db.collection("transits").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _transits.postValue(snapshot.toObjects(Transit::class.java))
        }

        db.collection("lieux").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _lieu.postValue(snapshot.toObjects(Lieu::class.java))
        }

        db.collection("voyages").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _voyages.postValue(snapshot.toObjects(Voyage::class.java))
        }
    }
}