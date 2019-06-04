package org.pbreakers.mobile.getticket.view.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_enreg.*
import kotlinx.android.synthetic.main.fragment_enreg.view.*
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.util.Tools
import org.pbreakers.mobile.getticket.util.ViewAnimation


class EnregFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enreg, container, false)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agency
        initAgencyComponent(view)
    }

    private fun initAgencyComponent(view: View) {
        view.btnToggleAgency.setOnClickListener {
            toggleSection(it, view.lytExpandAgency)
        }

        view.btnHideAgency.setOnClickListener {
            toggleSection(view.btnToggleAgency, view.lytExpandAgency)
        }

        view.btnEnregAgency.setOnClickListener {
            if (view.edtNomAgency.text.isBlank()) {
                view.edtNomAgency.error = getString(R.string.input_empty)
            } else {
                // Todo: Save agency
            }
        }
    }

    // The [view] represent the toggle and [layout] the layout that will be hided
    private fun toggleSection(view: View, layout: LinearLayout) {

        val show = toggleArrow(view)

        if (show) {
            ViewAnimation.expand(layout, object : ViewAnimation.AnimListener {
                override fun onFinish() {
                    Tools.nestedScrollTo(nestedScrollView, layout)
                }
            })

        } else {
            ViewAnimation.collapse(layout)
        }
    }

    private fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }
}
