package org.pbreakers.mobile.getticket.view.fragment


import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bus_detail.*
import kotlinx.android.synthetic.main.fragment_bus_detail.view.*

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.util.Tools


class BusDetailFragment : BottomSheetDialogFragment() {


    private lateinit var rootView: View
    private lateinit var behavior: BottomSheetBehavior<View>
    lateinit var bus: Bus

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        rootView = View.inflate(context, R.layout.fragment_bus_detail, null)

        dialog.setContentView(rootView)

        behavior = BottomSheetBehavior.from(rootView.parent as View)
        behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        // (rootView.lyt_spacer as View).minimumHeight = Tools.getHeightScreen() / 2
        hideView(rootView.app_bar_layout)

        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    showView(rootView.app_bar_layout, getActionBarSize())
                    // hideView(rootView.lyt_profile)
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    hideView(rootView.app_bar_layout)
                    // showView(rootView.lyt_profile, getActionBarSize())
                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        rootView.bt_close.setOnClickListener {
            dismiss()
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hideView(view: View) {
        val params = view.layoutParams
        params.height = 0
        view.layoutParams = params
    }

    private fun showView(view: View, size: Int) {
        val params = view.layoutParams
        params.height = size
        view.layoutParams = params
    }

    private fun getActionBarSize(): Int {
        val styledAttributes = context!!.theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        return styledAttributes.getDimension(0, 0f).toInt()
    }
}
