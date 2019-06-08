package org.pbreakers.mobile.getticket.util

import android.widget.EditText
import android.widget.Spinner
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    print("31/12/2019".getDateFromString()?.getFormattedDate("dd/mm/yyyy"))
}

fun EditText.isInvalidInput(errorMessage: String): Boolean {
    return if (text.isBlank()) {
        error = errorMessage
        true
    } else {
        false
    }
}

fun cleanText(vararg inputs: EditText) {
    for (input in inputs) {
        input.text.clear()
    }
}

fun String.getDateFromString(): Date? {
    val regex = """^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$""".toRegex()

    return if (regex.matches(this)) {
        SimpleDateFormat("dd/mm/yyyy", Locale.FRANCE).parse(this)
    } else {
        null
    }
}

fun String.getTimeFromString(): Date? {
    val regex = """([01]?[0-9]|2[0-3]):[0-5][0-9]""".toRegex()

    return if (regex.matches(this)) {
        SimpleDateFormat("HH:mm", Locale.FRANCE).parse(this)
    } else {
        null
    }
}

fun Spinner.itemIsSelected(errorMessage: String): Boolean {

    return if (selectedItem == null) {
        context.toast(errorMessage)
        false
    } else {
        true
    }
}


fun Date.getFormattedDate(format: String): String? {
    return try {
        val formatter = SimpleDateFormat(format, Locale.FRANCE)
        formatter.format(this)
    } catch (exp: Exception) {
        null
    }
}