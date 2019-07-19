package org.pbreakers.mobile.getticket.view.fragment


import android.Manifest
import android.app.Activity
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
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.kinda.alert.KAlertDialog
import kotlinx.android.synthetic.main.fragment_scanning.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
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

        when(billet.idEtat) {

            Etat.CONSOMMER -> {
                dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                dialog.contentText = "Ce billet est deja consommer"
                dialog.titleText = "Erreur"
            }

            Etat.ATTENTE -> {
                dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                dialog.contentText = "L'administrateur n'a pas encore valider le billet !"
                dialog.titleText = "Erreur"
            }

            Etat.IN_PROGRESS -> {
                dialog.dismissWithAnimation()
                changeTicketStateAndShowDetail(billet)
            }

            else -> {
                dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                dialog.titleText = "Etat"
                dialog.contentText = "Impossible de verifier l'etat du billet ..."
            }
        }
    }

    private fun changeTicketStateAndShowDetail(billet: Billet) {

        val task = scannerViewModel.updateBillet(billet)

        task.addOnSuccessListener {
            val bundle = bundleOf("billet" to billet)
            Navigation.findNavController(btnLaunchScanner)
                .navigate(R.id.action_scanningFragment_to_detailBilletFragment, bundle)
        }

        task.addOnFailureListener {
            dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
            dialog.titleText = "Etat"
            dialog.contentText = "Impossible de modifier l'etat du billet ..."
        }
    }
}
