package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import kotlinx.android.synthetic.main.fragment_signaler_abus.view.*
import org.jetbrains.anko.design.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentSignalerAbusBinding
import org.pbreakers.mobile.getticket.util.cleanText
import org.pbreakers.mobile.getticket.util.isInvalidInput
import org.pbreakers.mobile.getticket.viewmodel.SignalerAbusViewModel


class SignalerAbusFragment : Fragment() {

    private val signalerAbusViewModel by viewModel<SignalerAbusViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentSignalerAbusBinding>(inflater, R.layout.fragment_signaler_abus, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = signalerAbusViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btnSubmit.setOnClickListener {
            if (view.edtMessage.isInvalidInput(getString(R.string.input_empty))) {
                view.edtMessage.error = getString(R.string.input_empty)
                return@setOnClickListener
            }

            val message = view.edtMessage.text.trim()

            cleanText(view.edtMessage)
            view.snackbar("Votre commentaire a été envoyé avec success !")
        }
    }
}
