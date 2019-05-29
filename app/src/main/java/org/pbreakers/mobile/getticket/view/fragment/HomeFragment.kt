package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentHomeBinding
import org.pbreakers.mobile.getticket.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private val homeViewModel by lazy {
        ViewModelProviders.of(this).get<HomeViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = homeViewModel

        return binding.root
    }
}
