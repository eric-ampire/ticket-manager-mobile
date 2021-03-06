package org.pbreakers.mobile.getticket.util

import android.content.Context
import android.widget.EditText
import android.widget.Spinner
import com.kinda.alert.KAlertDialog
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


fun KAlertDialog.modifierDialog(type: Int, title: String, content: String) {

    changeAlertType(type)
    contentText = content
    titleText = title
}

// Todo: i18n
fun KAlertDialog.dialogSuccess(message: String) {
    changeAlertType(KAlertDialog.SUCCESS_TYPE)
    contentText = message
    titleText = "Success"
}

fun getDialogInstance(context: Context): KAlertDialog {
    return KAlertDialog(context, KAlertDialog.WARNING_TYPE)
}

fun KAlertDialog.dialogError(message: String) {
    changeAlertType(KAlertDialog.ERROR_TYPE)
    contentText = message
    titleText = "Erreur"
}

fun KAlertDialog.dialogProgress(message: String) {
    changeAlertType(KAlertDialog.PROGRESS_TYPE)
    contentText = message
    titleText = "Chargement"
}

fun KAlertDialog.dialogWarning(message: String) {
    changeAlertType(KAlertDialog.WARNING_TYPE)
    contentText = message
    titleText = "Avertissement"
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
        SimpleDateFormat("HH:mm", Locale.getDefault()).parse(this)
    } else {
        null
    }
}

fun Spinner.itemIsNotSelected(errorMessage: String): Boolean {

    return if (selectedItem == null) {
        context.toast(errorMessage)
        true
    } else {
        false
    }
}


fun Date.getFormattedDate(format: String): String? {
    return try {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.format(this)
    } catch (exp: Exception) {
        null
    }
}