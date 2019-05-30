package org.pbreakers.mobile.getticket.view.fragment


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import kotlinx.android.synthetic.main.item_add_bus.view.*
import org.jetbrains.anko.doIfSdk
import org.pbreakers.mobile.eduquelib.adapter.OnItemClickListener

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.databinding.FragmentBusBinding
import org.pbreakers.mobile.getticket.model.dao.BusDao
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.viewmodel.BusViewModel
import javax.inject.Inject


class BusFragment : Fragment() {

    private val busViewModel by lazy {
        ViewModelProviders.of(this).get<BusViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = activity?.application as App
        app.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentBusBinding>(inflater, R.layout.fragment_bus, container, false).apply {
            viewModel = busViewModel
            fabAddBus.setOnClickListener {
                showAddBusDialog(it)
            }
        }

        if (savedInstanceState == null) {
            busViewModel.init(this)
        }

        return binding.root
    }

    private fun showAddBusDialog(view: View) {
        val dialog = Dialog(this.context!!)
        val dialogView = View.inflate(this.context!!, R.layout.item_add_bus, null)

        dialogView.btnAddBus.setOnClickListener {
            dialog.dismiss()
        }

        dialogView.spinnerAgency


        dialog.setContentView(dialogView)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window!!.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }
}
