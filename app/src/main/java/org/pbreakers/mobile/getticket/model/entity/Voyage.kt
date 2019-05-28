package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    indices = [Index("idProvenance", "idDestination", "idBus")],
    foreignKeys = [
        ForeignKey(entity = Lieu::class, parentColumns = ["id"], childColumns = ["idProvenance"]),
        ForeignKey(entity = Lieu::class, parentColumns = ["id"], childColumns = ["idProvenance"]),
        ForeignKey(entity = Bus::class, parentColumns = ["id"], childColumns = ["idBus"])
    ]
)
@Parcelize
data class Voyage(
    @PrimaryKey val id: Long,
    val reference: String,
    val idProvenance: Long,
    val idDestination: Long,
    val idBus: Long,
    val dateDepart: Date,
    val heureDepart: Date
) : Parcelable