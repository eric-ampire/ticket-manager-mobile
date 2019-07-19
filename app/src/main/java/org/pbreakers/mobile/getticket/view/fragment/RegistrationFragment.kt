package org.pbreakers.mobile.getticket.view.fragment


import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kinda.alert.KAlertDialog
import kotlinx.android.synthetic.main.fragment_registration.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentRegistrationBinding
import org.pbreakers.mobile.getticket.util.*
import org.pbreakers.mobile.getticket.view.activity.MainActivity
import org.pbreakers.mobile.getticket.viewmodel.AuthViewModel

class RegistrationFragment : Fragment() {

    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegistrationBinding.inflate(inflater).apply {
            viewModel = authViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val dialog = getDialogInstance(requireContext()).apply {
            dialogProgress("Opération en cour d'execution")
            setCancelable(false)
        }

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.SUCCESS_TYPE) {
                dialog.dismissWithAnimation()
                startActivity(Intent(activity, MainActivity::class.java))
                requireActivity().finish()
            } else {
                dialog.dismissWithAnimation()
            }
        }

        authViewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.RUNNING -> dialog.show()
                LoadingState.Status.ERROR -> dialog.dialogError(it.message ?: "Probleme inconnue !")
                LoadingState.Status.LOADED -> dialog.dialogSuccess("Authentification success")
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignUp.setOnClickListener {
            when {
                edtEmailUser.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                edtFullName.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                edtPassword.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                edtConfirm.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener

                edtPassword.text.toString() != edtConfirm.text.toString() -> return@setOnClickListener
            }

            signUp()
        }

        cbxShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                edtConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                edtConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun signUp() {
        authViewModel.signUp()
    }
}
