package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//@Entity
@Parcelize
data class Lieu(
    val idLieu: String = "",
    val nomLieu: String = ""
) : Parcelable {
    override fun toString(): String = nomLieu
}