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
        const val CLIENT: Long = 2
        const val ADMIN: Long = 1
        const val HOTESSE: Long = 3
        const val AGENT_LOCAL: Long = 4
    }
}