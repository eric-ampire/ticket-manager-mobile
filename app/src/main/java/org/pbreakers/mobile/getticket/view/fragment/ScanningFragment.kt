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
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scanning.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.pbreakers.mobile.getticket.R


class ScanningFragment : Fragment(), ZXingScannerView.ResultHandler {


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
            qrCodeScanner.run {
                stopCamera()
                startCamera()
                setResultHandler(this@ScanningFragment)
            }
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
            Toast.makeText(context, result.text, Toast.LENGTH_LONG).show()
        }
    }

    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }
}
