package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    indices = [Index("idProvenance", "idDestination", "idBus", "idTransit", "idEtat")],
    foreignKeys = [
        ForeignKey(entity = Lieu::class, parentColumns = ["idLieu"], childColumns = ["idProvenance"]),
        ForeignKey(entity = Lieu::class, parentColumns = ["idLieu"], childColumns = ["idDestination"]),
        ForeignKey(entity = Bus::class, parentColumns = ["idBus"], childColumns = ["idBus"]),
        ForeignKey(entity = Etat::class, parentColumns = ["idEtat"], childColumns = ["idEtat"]),
        ForeignKey(entity = Transit::class, parentColumns = ["idTransit"], childColumns = ["idTransit"])
    ]
)
@Parcelize
data class Voyage(
    @PrimaryKey val idVoyage: Long,
    val referenceVoyage: String,
    val idProvenance: Long,
    val idDestination: Long,
    val idBus: Long,
    val dateDepart: Date,
    val heureDepart: Date,
    val dateDestination: Date,
    val heureDestinatin: Date,
    val idTransit: Long,
    val prixVoyage: Double,
    val idEtat: Long
) : Parcelable {
    override fun toString(): String = referenceVoyage
}