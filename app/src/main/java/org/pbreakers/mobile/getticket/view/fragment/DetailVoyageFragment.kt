package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_voyage_detail.*
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentVoyageDetailBinding
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.util.Tools.toggleSection
import org.pbreakers.mobile.getticket.viewmodel.DetailVoyageViewModel


class DetailVoyageFragment : Fragment() {

    // Todo: You have to use the constant
    private val currentVoyage by lazy {
        arguments?.getParcelable<Voyage>("voyage")
    }

    private val detailVoyageViewModel by lazy {
        ViewModelProviders.of(this).get(DetailVoyageViewModel::class.java).apply {
            voyage = currentVoyage!!
            init()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentVoyageDetailBinding>(
            inflater, R.layout.fragment_voyage_detail, container, false).apply {

            voyage = currentVoyage
            viewModel = detailVoyageViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }

    private fun initComponent() {
        // info item_section
        btnToggleInfo.setOnClickListener { toggleSection(btnToggleInfo, lytExpandInfo, nestedScrollView) }
        btnHideInfo.setOnClickListener { toggleSection(btnToggleInfo, lytExpandInfo, nestedScrollView) }

        // passenger item_section
        btnTogglePassenger.setOnClickListener { toggleSection(btnTogglePassenger, lytExpandPassenger, nestedScrollView) }

        btnReserve.setOnClickListener {
            // reservation
        }
    }
}
