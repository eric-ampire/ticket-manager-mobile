package org.pbreakers.mobile.getticket.util

import android.widget.EditText

fun EditText.isInvalidInput(errorMessage: String): Boolean {
    return if (text.isBlank()) {
        error = errorMessage
        true
    } else {
        false
    }
}