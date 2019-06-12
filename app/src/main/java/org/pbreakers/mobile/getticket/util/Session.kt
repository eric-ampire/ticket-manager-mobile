package org.pbreakers.mobile.getticket.util

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import javax.inject.Inject


class Session @Inject constructor(private val preference: SharedPreferences, private val gson: Gson) {

    fun createSession(user: Utilisateur) {
        val json = gson.toJson(user)

        preference.edit {
            putString(USER_OBJECT, json)
        }
    }

    fun getCurrentUser(): Utilisateur? {
        val jsonObject = preference.getString(USER_OBJECT, null)

        return if (jsonObject == null) {
            null
        } else {
            gson.fromJson(jsonObject, Utilisateur::class.java)
        }
    }

    fun destroy() {
        preference.edit {
            remove(USER_OBJECT)
        }
    }

    companion object {
        private const val USER_OBJECT = "USER_OBJECT"
    }
}