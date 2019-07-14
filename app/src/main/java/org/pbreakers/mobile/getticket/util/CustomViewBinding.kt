package org.pbreakers.mobile.getticket.util

import android.graphics.drawable.Drawable
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.pbreakers.mobile.getticket.R
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
fun bindSpinnerAdapter(spinner: Spinner, data: List<*>?) {

    if (data == null) return

    ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, data).run {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = this
    }
}

@BindingAdapter(value = ["bindDate"])
fun TextView.bindDate(date: Date?) {
    if (date == null) {
        text = Date().getFormattedDate(context.getString(R.string.date_format))
    } else {
        text = date.getFormattedDate(context.getString(R.string.date_format))
    }
}

@BindingAdapter(value = ["bindDateAndTime"])
fun TextView.bindDateAndTime(date: Date?) {
    if (date == null) {
        text = "- - -"
    } else {
        text = date.getFormattedDate(context.getString(R.string.datetime_format))
    }
}

@BindingAdapter(value = ["bindTime"])
fun TextView.bindTime(date: Date?) {
    if (date == null) {
        text = Date().getFormattedDate(context.getString(R.string.time_format))
    } else {
        text = date.getFormattedDate(context.getString(R.string.time_format))
    }
}