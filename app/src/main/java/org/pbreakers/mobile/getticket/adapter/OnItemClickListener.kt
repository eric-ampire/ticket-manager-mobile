package org.pbreakers.mobile.getticket.adapter

import android.view.View

interface OnItemClickListener<I> {
    fun onClick(view: View, item: I, position: Int)
    fun onLongClick(view: View, item: I, position: Int) {

    }
}