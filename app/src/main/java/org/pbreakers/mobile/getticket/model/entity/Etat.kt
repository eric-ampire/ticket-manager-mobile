package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//@Entity
@Parcelize
data class Etat(
    val idEtat: String = "",
    val nomEtat: String = ""
) : Parcelable {
    override fun toString(): String = nomEtat

    companion object {
        const val CONSOMMER = "consommer"
        const val IN_PROGRESS = "progress"
        const val UNAVAILABLE = "unvailable"
        const val FINISHED = "finished"
        const val ATTENTE = "attente"
    }
}