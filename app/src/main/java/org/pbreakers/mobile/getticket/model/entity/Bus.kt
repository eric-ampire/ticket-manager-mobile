package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = [Index("idAgent")],
    foreignKeys = [ForeignKey(entity = Agence::class, parentColumns = ["id"], childColumns = ["idAgence"])]
)
@Parcelize
class Bus(
    @PrimaryKey val id: Long,
    val nom: String,
    val nombreRange: Int,
    val nombreSiege: Int,
    val idAgence: Long
) : Parcelable