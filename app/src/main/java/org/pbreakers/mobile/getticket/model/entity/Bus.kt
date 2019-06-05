package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = [Index("idAgence")],
    foreignKeys = [ForeignKey(entity = Agence::class, parentColumns = ["idAgence"], childColumns = ["idAgence"])]
)
@Parcelize
data class Bus(
    @PrimaryKey val idBus: Long,
    val nomBus: String,
    val nombreRange: Int,
    val nombreSiege: Int,
    val idAgence: Long
) : Parcelable {
    override fun toString(): String = nomBus
}