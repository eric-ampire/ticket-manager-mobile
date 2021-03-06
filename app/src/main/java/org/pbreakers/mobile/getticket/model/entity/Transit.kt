package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Transit(
    val idTransit: String = "",
    val nomTransit: String = ""
) : Parcelable {
    override fun toString(): String = nomTransit
}