package org.pbreakers.mobile.getticket.view.fragment


import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import org.pbreakers.mobile.eduquelib.adapter.OnItemClickListener
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.databinding.FragmentHomeBinding
import org.pbreakers.mobile.getticket.di.component.AppComponent
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.viewmodel.HomeViewModel


class HomeFragment : Fragment(), OnItemClickListener<Voyage> {

    private val homeViewModel by lazy {
        ViewModelProviders.of(this).get<HomeViewModel>().apply {
            this.adapter = VoyageAdapter(this@HomeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false).apply {
            this.viewModel = homeViewModel
        }

        homeViewModel.init()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onClick(view: View, item: Voyage, position: Int) {
        showDetailVoyageDialog(item, view)
    }

    private fun showDetailVoyageDialog(item: Voyage, view: View) {
//        val dialog = Dialog(this.context!!).apply {
//            setContentView(R.layout.fragment_voyage_detail)
//            setCancelable(true)
//        }
//
//        val layoutParams = WindowManager.LayoutParams().apply {
//            copyFrom(dialog.window!!.attributes)
//            width = WindowManager.LayoutParams.MATCH_PARENT
//        }
//
//        dialog.show()
//        dialog.window!!.attributes = layoutParams
        val navigation = findNavController(view)
        navigation.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.detailVoyageFragment) {
                (activity as AppCompatActivity).supportActionBar?.hide()
            } else {
                (activity as AppCompatActivity).supportActionBar?.show()
            }
        }

        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailVoyageFragment)

    }
}
