package org.pbreakers.mobile.getticket.view.fragment


import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import com.kinda.alert.KAlertDialog
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.cbxShowPassword
import kotlinx.android.synthetic.main.fragment_registration.edtPassword
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentRegistrationBinding
import org.pbreakers.mobile.getticket.util.isInvalidInput
import org.pbreakers.mobile.getticket.view.activity.MainActivity
import org.pbreakers.mobile.getticket.viewmodel.AuthViewModel

class RegistrationFragment : Fragment() {

    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflate<FragmentRegistrationBinding>(inflater, R.layout.fragment_registration, container, false).apply {
            viewModel = authViewModel
            lifecycleOwner = viewLifecycleOwner
        }

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
        val dialog = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE).apply {
            titleText = "Sign Up"
            contentText = "Operation en cour d'execution"
            setCancelable(false)
            show()
        }

        authViewModel.signUp({
            dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)

        }) {
            dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
            dialog.titleText = "Erreur"
            dialog.contentText = it
        }

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.SUCCESS_TYPE) {
                dialog.dismissWithAnimation()
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            } else {
                dialog.dismissWithAnimation()
            }
        }
    }
}
