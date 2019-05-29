package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = [Index("idLieu", "idVoyage")],
    foreignKeys = [
        ForeignKey(entity = Lieu::class, parentColumns = ["idLieu"], childColumns = ["idLieu"]),
        ForeignKey(entity = Voyage::class, parentColumns = ["idVoyage"], childColumns = ["idVoyage"])
    ]
)
@Parcelize
data class PointArret(
    @PrimaryKey val idPointArret: Long,
    val idLieu: Long,
    val idVoyage: Long
) : Parcelable