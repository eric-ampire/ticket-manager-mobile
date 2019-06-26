package org.pbreakers.mobile.getticket.model.entity

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Abus (
    val id: String = "",
    @field:ServerTimestamp val date: Date = Date(),
    val idUser: String = "",
    val idBus: String = "",
    val message: String = ""
) : Parcelable