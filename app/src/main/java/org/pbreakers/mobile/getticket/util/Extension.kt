package org.pbreakers.mobile.getticket.util

import android.widget.EditText

fun EditText.isValidInput(errorMessage: String): Boolean {
    return if (text.isBlank()) {
        error = errorMessage
        false
    } else {
        true
    }
}