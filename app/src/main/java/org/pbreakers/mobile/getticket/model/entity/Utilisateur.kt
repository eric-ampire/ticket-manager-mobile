package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = [Index("idRole")],
    foreignKeys = [ForeignKey(entity = Role::class, parentColumns = ["id"], childColumns = ["idRole"])]
)
@Parcelize
class Utilisateur(
    @PrimaryKey val id: Long,
    val nom: String,
    val pseudo: String,
    val password: String,
    val idRole: Long
) : Parcelable