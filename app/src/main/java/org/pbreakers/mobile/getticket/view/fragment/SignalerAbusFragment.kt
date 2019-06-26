package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kinda.alert.KAlertDialog
import kotlinx.android.synthetic.main.fragment_enreg.view.*
import kotlinx.android.synthetic.main.fragment_signaler_abus.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentSignalerAbusBinding
import org.pbreakers.mobile.getticket.model.entity.Abus
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.util.cleanText
import org.pbreakers.mobile.getticket.util.isInvalidInput
import org.pbreakers.mobile.getticket.util.modifierDialog
import org.pbreakers.mobile.getticket.viewmodel.SignalerAbusViewModel
import java.util.*


class SignalerAbusFragment : Fragment() {

    private val signalerAbusViewModel by viewModel<SignalerAbusViewModel>()
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

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
            if (view.spinnerBus.selectedItem == null) {
                context?.toast("Selectionner un bus")
                return@setOnClickListener
            }

            if (view.edtMessage.isInvalidInput(getString(R.string.input_empty))) {
                view.edtMessage.error = getString(R.string.input_empty)
                return@setOnClickListener
            }

            val message = view.edtMessage.text.toString().trim()
            val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
            val selectedBus = view.spinnerBus.selectedItem as Bus
            val abusRef = db.collection("abus").document()

            val abus = Abus(
                id = abusRef.id,
                date = Date(),
                idBus = selectedBus.idBus,
                idUser = currentUserId,
                message = message
            )

            val dialog = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE).apply {
                contentText = "Envoie en cour"
                titleText = "Envoi..."
                setCancelable(false)
                show()
            }

            dialog.setConfirmClickListener {
                if (it.alerType == KAlertDialog.SUCCESS_TYPE) {
                    it.dismissWithAnimation()
                }
            }

            abusRef.set(abus).addOnCompleteListener {
                if (it.isSuccessful) {
                    dialog.modifierDialog(KAlertDialog.SUCCESS_TYPE, "Envoi", "Votre commentaire a été envoyé avec success !")
                    cleanText(view.edtMessage)
                } else {
                    dialog.modifierDialog(KAlertDialog.ERROR_TYPE, "Envoi", it.exception?.message ?: "Erreur inconnue")
                }
            }
        }
    }
}
