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
        ForeignKey(entity = Lieu::class, parentColumns = ["id"], childColumns = ["idLieu"]),
        ForeignKey(entity = Voyage::class, parentColumns = ["id"], childColumns = ["idVoyage"])
    ]
)
@Parcelize
class PointArret(
    @PrimaryKey val id: Long,
    val idLieu: Long,
    val idVoyage: Long
) : Parcelable