package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Agence(
    val idAgence: String = "",
    val nomAgence: String = ""
) : Parcelable {
    override fun toString(): String = nomAgence
}