package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail_billet.view.*
import net.glxn.qrgen.android.QRCode
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentDetailBilletBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.viewmodel.DetailBilletViewModel


class DetailBilletFragment : Fragment() {

    private val currentBillet by lazy {
        DetailBilletFragmentArgs.fromBundle(arguments!!).billet
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailBilletViewModel: DetailBilletViewModel by viewModel {
            parametersOf(currentBillet)
        }

        val binding by lazy {
            FragmentDetailBilletBinding.inflate(inflater).apply {
                viewModel = detailBilletViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val qrCodeBitmap = QRCode.from(currentBillet.idBillet).bitmap()
        view.ivQrCodeBillet.setImageBitmap(qrCodeBitmap)
    }
}
