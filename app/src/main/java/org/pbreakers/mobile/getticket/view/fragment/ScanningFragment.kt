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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.kinda.alert.KAlertDialog
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_scanning.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.viewmodel.ScannerViewModel


class ScanningFragment : Fragment(), ZXingScannerView.ResultHandler {

    private val scannerViewModel by lazy {
        ViewModelProviders.of(this).get(ScannerViewModel::class.java)
    }


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
            val dialog = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE).apply {
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

            val idBillet = result.text.toLongOrNull()
            if (idBillet == null) {
                dialog.titleText = "Erreur"
                dialog.contentText = "Le QR Code n'est pas valide"
                dialog.changeAlertType(KAlertDialog.ERROR_TYPE)

            } else {
                findBilletById(idBillet, dialog)
            }
        }
    }

    private fun findBilletById(idBillet: Long, dialog: KAlertDialog) {
        scannerViewModel.findBilletById(idBillet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Billet> {

                override fun onSuccess(billet: Billet) {
                    isValidTicket(billet, dialog)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    dialog.titleText = "Erreur"
                    dialog.contentText = e.message
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            })

    }

    private fun isValidTicket(billet: Billet, dialog: KAlertDialog) {
        when(billet.idEtat) {
            1L -> {
                dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                dialog.contentText = "Ce billet est deja consommer"
                dialog.titleText = "Erreur"
            }

            3L -> {
                dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                dialog.contentText = "L'administrateur n'a pas encore valider le billet !"
                dialog.titleText = "Erreur"
            }

            2L -> {
                showDetailBillet(billet)
            }
        }
    }

    private fun showDetailBillet(billet: Billet) {

    }

    // Todo: ca ne doit pas etre ici
    private fun updateEtatBillet(billet: Billet, dialog: KAlertDialog) {
        billet.apply { idEtat = 4 }

        scannerViewModel.updateBillet(billet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    dialog.titleText = "Erreur"
                    dialog.contentText = e.message
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            })

    }

    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }
}
