package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.kinda.alert.KAlertDialog
import dagger.Component
import io.reactivex.MaybeObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.design.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentLoginBinding
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.util.modifierDialog
import org.pbreakers.mobile.getticket.viewmodel.AuthViewModel


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

        login(view)
    }

    private fun login(view: View) {
        val dialog = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE).apply {
            contentText = "Verification en cour.."
            titleText = "Login"
        }

        authViewModel.login()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<Utilisateur> {
                override fun onComplete() {
                    dialog.modifierDialog(KAlertDialog.ERROR_TYPE, "Error", "Aucun utilisateur n'a ete trouver")
                }

                override fun onSuccess(t: Utilisateur) {
                    dialog.modifierDialog(KAlertDialog.SUCCESS_TYPE, "Success", "Authentification success")
                }

                override fun onSubscribe(d: Disposable) {
                    dialog.show()
                }

                override fun onError(e: Throwable) {
                    dialog.modifierDialog(KAlertDialog.ERROR_TYPE, "Error", e.message ?: "Erreur inconnue")
                }
            })

    }
}
