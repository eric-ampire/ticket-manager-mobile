package org.pbreakers.mobile.getticket.view.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.kinda.alert.KAlertDialog
import com.kinda.alert.KAlertDialog.ERROR_TYPE
import com.kinda.alert.KAlertDialog.SUCCESS_TYPE
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.design.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentLoginBinding
import org.pbreakers.mobile.getticket.util.*
import org.pbreakers.mobile.getticket.view.activity.MainActivity
import org.pbreakers.mobile.getticket.viewmodel.AuthViewModel
import android.text.method.HideReturnsTransformationMethod as ShowPassword
import android.text.method.PasswordTransformationMethod as HidePassword


class LoginFragment : Fragment() {

    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false).apply {
            viewModel = authViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.btnLogin.setOnClickListener {
            credentialChecking(it)
        }

        return binding.root
    }

    private fun credentialChecking(view: View) {
        if (authViewModel.pseudo.value == null) {
            view.snackbar("Pseudo invalide !")
            return
        }

        if (authViewModel.password.value == null) {
            view.snackbar("Mot de passe invalide")
            return
        }

        login()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCreateAccount.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        cbxShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                edtPassword.transformationMethod = ShowPassword.getInstance()
            } else {
                edtPassword.transformationMethod = HidePassword.getInstance()
            }
        }
    }

    private fun login() {
        val dialog = getDialogInstance(requireContext()).apply {
            dialogProgress("Authentification en cour...")
        }

        authViewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.RUNNING -> dialog.show()
                LoadingState.Status.ERROR -> dialog.dialogError(it.message ?: "Probleme inconnue !")
                LoadingState.Status.LOADED -> dialog.dialogSuccess("Authentification success")
            }
        })

        authViewModel.login()

        dialog.setConfirmClickListener {
            if (it.alerType == SUCCESS_TYPE) {
                startActivity(Intent(context, MainActivity::class.java))
            } else {
                dialog.dismissWithAnimation()
            }
        }
    }
}
