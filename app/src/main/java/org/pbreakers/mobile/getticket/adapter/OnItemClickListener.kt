package org.pbreakers.mobile.eduquelib.adapter

import android.view.View

interface OnItemClickListener<I> {
    fun onClick(view: View, item: I, position: Int)
}