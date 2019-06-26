package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Utilisateur(
    val idUtilisateur: String = "",
    val nomUtilisateur: String = "",
    val pseudoUtilisateur: String = "",
    val passwordUtilisateur: String = "",
    val idRole: String = ""
) : Parcelable {
    override fun toString(): String {
        return nomUtilisateur
    }
}