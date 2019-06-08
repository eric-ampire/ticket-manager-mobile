package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_voyage_detail.*
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.util.Tools
import org.pbreakers.mobile.getticket.util.Tools.toggleArrow
import org.pbreakers.mobile.getticket.util.Tools.toggleSection
import org.pbreakers.mobile.getticket.util.ViewAnimation


class DetailVoyageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voyage_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }

    private fun initComponent() {
        // info item_section
        btnToggleInfo.setOnClickListener {
            toggleSection(btnToggleInfo, lytExpandInfo, nestedScrollView)
        }
        btnHideInfo.setOnClickListener {
            toggleSection(btnToggleInfo, lytExpandInfo, nestedScrollView)
        }

        // passenger item_section
        btnTogglePassenger.setOnClickListener {
            toggleSection(btnTogglePassenger, lytExpandPassenger, nestedScrollView)
        }

        // copy to clipboard
        btnReserve.setOnClickListener {
            // reservation
        }
    }
}
