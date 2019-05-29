package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class Agence(
    @PrimaryKey val idAgence: Long,
    val nomAgence: String
) : Parcelable