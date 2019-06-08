package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentModifierVoyageBinding
import org.pbreakers.mobile.getticket.model.entity.Voyage


class ModifierVoyageFragment : Fragment() {

    private lateinit var binding: FragmentModifierVoyageBinding
    private val currentVoyage by lazy {
        arguments?.getParcelable<Voyage>("voyage")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflate<FragmentModifierVoyageBinding>(inflater, R.layout.fragment_modifier_voyage, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            voyage = currentVoyage
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_save -> {
                true
            }

            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}
