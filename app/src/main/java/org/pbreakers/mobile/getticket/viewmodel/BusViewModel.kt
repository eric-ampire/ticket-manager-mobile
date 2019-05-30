package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.app.Dialog
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import org.pbreakers.mobile.eduquelib.adapter.OnItemClickListener
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import org.pbreakers.mobile.getticket.view.fragment.BusDetailFragment
import javax.inject.Inject

class BusViewModel(val app: Application) : AndroidViewModel(app), OnItemClickListener<Bus> {

    @Inject lateinit var repository: BusRepository

    var busAdapter = BusAdapter(this)
    private lateinit var fragment: Fragment

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun init(fragment: Fragment) {

        this.fragment = fragment

        val config = PagedList.Config.Builder()
            .setPageSize(3)
            .build()
        val data = LivePagedListBuilder(repository.findAll(), config).build()

        data.observeForever {
            busAdapter.submitList(it)
        }
    }

    override fun onClick(view: View, item: Bus, position: Int) {
        val busDetailFragment = BusDetailFragment().apply {
            this.bus = item
        }

        val hostActivity = fragment.activity as AppCompatActivity
        busDetailFragment.show(hostActivity.supportFragmentManager, busDetailFragment.tag)
    }
}