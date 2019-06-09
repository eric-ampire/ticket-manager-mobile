package org.pbreakers.mobile.getticket.util

import android.R
import android.graphics.drawable.Drawable
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.*


@BindingAdapter(value = ["setAdapter"])
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = true)
fun bindImageUrl(imageView: ImageView, url: String?, placeholder: Drawable) {
    Picasso.get().load(url)
        .error(placeholder)
        .placeholder(placeholder)
        .into(imageView)
}

@BindingAdapter(value = ["setAdapter"])
fun bindSpinnerAdapter(spinner: Spinner, data: List<*>) {

    ArrayAdapter(spinner.context, R.layout.simple_spinner_item, data).run {
        setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = this
    }
}

@BindingAdapter(value = ["bindDate"])
fun bindDate(textView: TextView, date: Date?) {
    if (date == null) {
        textView.text = "- - -"
    } else {
        textView.text = date.getFormattedDate("dd/MM/yyyy")
    }
}

@BindingAdapter(value = ["bindDateAndTime"])
fun bindDateAndTime(textView: TextView, date: Date?) {
    if (date == null) {
        textView.text = "- - -"
    } else {
        textView.text = date.getFormattedDate("dd/MM/yyyy à HH:mm")
    }
}

@BindingAdapter(value = ["bindTime"])
fun bindTime(textView: TextView, date: Date?) {
    if (date == null) {
        textView.text = "- - -"
    } else {
        textView.text = date.getFormattedDate("HH:mm")
    }
}