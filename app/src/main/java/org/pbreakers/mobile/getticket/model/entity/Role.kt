package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Role(
    val idRole: String = "",
    val nomRole: String = ""
) : Parcelable {
    override fun toString() = nomRole

    companion object {
        const val CLIENT = "user"
        const val ADMIN = "admin"
        const val HOTESSE = "hotesse"
        const val AGENT_LOCAL = "agent"
    }
}