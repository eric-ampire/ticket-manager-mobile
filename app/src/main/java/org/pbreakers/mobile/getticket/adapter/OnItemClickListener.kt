package org.pbreakers.mobile.getticket.adapter

import android.view.View

interface OnItemClickListener<I> {
    fun onClick(view: View, item: I, position: Int)
    fun onClickPopupButton(view: View, item: I, position: Int)
}