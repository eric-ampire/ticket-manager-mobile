package org.pbreakers.mobile.getticket.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


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