package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    indices = [Index("idVoyage", "idUtilisateur", "idEtat")],
    foreignKeys = [
        ForeignKey(entity = Etat::class, parentColumns = ["idEtat"], childColumns = ["idEtat"]),
        ForeignKey(entity = Utilisateur::class, parentColumns = ["idUtilisateur"], childColumns = ["idUtilisateur"]),
        ForeignKey(entity = Voyage::class, parentColumns = ["idVoyage"], childColumns = ["idVoyage"])
    ]
)
@Parcelize
data class Billet(
    @PrimaryKey val idBillet: Long,
    val dateBillet: Date,
    val idVoyage: Long,
    val idUtilisateur: Long,
    val idEtat: Long
) : Parcelable