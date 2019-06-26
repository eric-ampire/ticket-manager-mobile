package org.pbreakers.mobile.getticket.view.fragment


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_scanning.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.viewmodel.ScannerViewModel
import kotlin.properties.Delegates


class ScanningFragment : Fragment(), ZXingScannerView.ResultHandler, KoinComponent {

    private val scannerViewModel by viewModel<ScannerViewModel>()
    private var dialog: KAlertDialog by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setScannerProperties()

        btnLaunchScanner.setOnClickListener {
            reloadScanner()
        }
    }

    private fun reloadScanner() {
        qrCodeScanner.run {
            stopCamera()
            startCamera()
            setResultHandler(this@ScanningFragment)
        }
    }

    private fun setScannerProperties() {

        qrCodeScanner.apply {
            setFormats(listOf(BarcodeFormat.QR_CODE))
            setAutoFocus(true)
            setLaserColor(R.color.colorAccent)
            setMaskColor(R.color.colorAccent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat
                    .requestPermissions(activity as Activity, arrayOf(Manifest.permission.CAMERA), 1)
                return
            }
        }

        qrCodeScanner.run {
            startCamera()
            setResultHandler(this@ScanningFragment)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }

    override fun onPause() {
        super.onPause()
        qrCodeScanner.stopCamera()
    }

    override fun handleResult(result: Result?) {
        if (result != null) {
            dialog = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE).apply {
                titleText = "Verification !"
                contentText = "Verification en cour d'execution..."
                show()
            }

            dialog.setConfirmClickListener {
                if (it.alerType == KAlertDialog.WARNING_TYPE || it.alerType == KAlertDialog.ERROR_TYPE) {
                    it.dismissWithAnimation()
                    reloadScanner()
                }
            }

            val idBillet = result.text.toString()
            findBilletById(idBillet)
        }
    }

    private fun findBilletById(idBillet: String) {
        scannerViewModel.findBilletById(idBillet)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val mBillet = it.result?.toObject(Billet::class.java) ?: return@addOnCompleteListener
                    isValidTicket(mBillet)
                } else {
                    dialog.titleText = "Erreur"
                    dialog.contentText = it.exception?.message ?: "Unknown error !"
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            }

    }

    private fun isValidTicket(billet: Billet) {
        val db = FirebaseFirestore.getInstance()
        val etatRef = db.collection("etats").document(billet.idEtat)

        dialog.run {
            titleText = "Etat"
            contentText = "Verification etat billet ..."
        }

        etatRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val ticketState = it.result?.toObject(Etat::class.java) ?: return@addOnCompleteListener

                when {
                    ticketState.nomEtat.contains("consomme", ignoreCase = true) -> {
                        dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                        dialog.contentText = "Ce billet est deja consommer"
                        dialog.titleText = "Erreur"
                    }

                    ticketState.nomEtat.contains("attente", ignoreCase = true) -> {
                        dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                        dialog.contentText = "L'administrateur n'a pas encore valider le billet !"
                        dialog.titleText = "Erreur"
                    }

                    ticketState.nomEtat.contains("cour", ignoreCase = true) -> {
                        dialog.dismissWithAnimation()
                        showDetailBillet(billet)
                    }

                    else -> {
                        dialog.run {
                            dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                            titleText = "Etat"
                            contentText = "Impossible de verifier l'etat du billet ..."
                        }
                    }
                }

            } else {
                context?.toast(it.exception?.message ?: "Erreur inconnue")
            }
        }
    }

    private fun showDetailBillet(billet: Billet) {
        val bundle = bundleOf("billet" to billet)
        Navigation.findNavController(btnLaunchScanner)
            .navigate(R.id.action_scanningFragment_to_detailBilletFragment, bundle)
    }

    // Todo: ca ne doit pas etre ici
    private fun updateEtatBillet(billet: Billet, dialog: KAlertDialog) {
//        billet.apply { idEtat = 4 }
//
//        scannerViewModel.updateBillet(billet)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : CompletableObserver {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                }
//
//                override fun onError(e: Throwable) {
//                    dialog.titleText = "Erreur"
//                    dialog.contentText = e.message
//                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
//                }
//            })

    }
}
