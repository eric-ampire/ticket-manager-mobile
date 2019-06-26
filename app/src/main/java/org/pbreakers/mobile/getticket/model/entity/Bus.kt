package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//@Entity(
//    indices = [Index("idAgence")],
//    foreignKeys = [ForeignKey(entity = Agence::class, parentColumns = ["idAgence"], childColumns = ["idAgence"])]
//)
@Parcelize
data class Bus(
    val idBus: String = "",
    val nomBus: String = "",
    val nombreRange: Int = 0,
    val nombreSiege: Int = 0,
    val idAgence: String = ""
) : Parcelable {
    override fun toString(): String = nomBus
}