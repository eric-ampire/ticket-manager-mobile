package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

//@Entity(
//    indices = [Index("idProvenance", "idDestination", "idBus", "idTransit", "idEtat")],
//    foreignKeys = [
//        ForeignKey(entity = Lieu::class, parentColumns = ["idLieu"], childColumns = ["idProvenance"]),
//        ForeignKey(entity = Lieu::class, parentColumns = ["idLieu"], childColumns = ["idDestination"]),
//        ForeignKey(entity = Bus::class, parentColumns = ["idBus"], childColumns = ["idBus"]),
//        ForeignKey(entity = Etat::class, parentColumns = ["idEtat"], childColumns = ["idEtat"]),
//        ForeignKey(entity = Transit::class, parentColumns = ["idTransit"], childColumns = ["idTransit"])
//    ]
//)
@Parcelize
data class Voyage(
    val idVoyage: String = "",
    val referenceVoyage: String = "",
    val idProvenance: String = "",
    val idDestination: String = "",
    val idBus: String = "",
    @field:ServerTimestamp val dateDepart: Date = Date(),
    @field:ServerTimestamp val heureDepart: Date = Date(),
    @field:ServerTimestamp val dateDestination: Date = Date(),
    @field:ServerTimestamp val heureDestination: Date = Date(),
    val idTransit: String = "",
    val prixVoyage: Double = 0.0,
    val idEtat: String = ""
) : Parcelable {
    override fun toString(): String = referenceVoyage
}