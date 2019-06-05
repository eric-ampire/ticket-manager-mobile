package org.pbreakers.mobile.getticket.view.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_enreg.*
import kotlinx.android.synthetic.main.fragment_enreg.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.databinding.FragmentEnregBinding
import org.pbreakers.mobile.getticket.model.dao.RoleDao
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.util.Tools
import org.pbreakers.mobile.getticket.util.ViewAnimation
import org.pbreakers.mobile.getticket.viewmodel.EnregViewModel
import java.util.*
import javax.inject.Inject
import java.util.Observer as Observer1


class EnregFragment : Fragment() {

    lateinit var binding: FragmentEnregBinding

    private val enregViewModel by lazy {
        ViewModelProviders.of(this).get<EnregViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflate<FragmentEnregBinding>(inflater, R.layout.fragment_enreg, container, false).apply {
            roles = listOf()
            lifecycleOwner = this@EnregFragment
        }

        enregViewModel.findRole().observe(viewLifecycleOwner, Observer {
            binding.roles = it
        })

        return binding.root
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agency
        initAgencyComponent(view)

        // Etat
        initEtatComponent(view)

        // Lieu
        initLieuComponent(view)

        // Role
        initRoleComponent(view)

        // Transit
        initTransitComponent(view)

        // User
        initUserComponent(view)
    }

    private fun initUserComponent(view: View) {
        view.btnToggleUser.setOnClickListener { toggleSection(it, view.lytExpandUser) }
        view.btnHideUser.setOnClickListener { toggleSection(view.btnToggleUser, view.lytExpandUser) }
        view.btnEnregUser.setOnClickListener {

            if (formValidation(view)) {

                val role = binding.edtRoleUser.selectedItem as Role

                val user = Utilisateur(
                    System.nanoTime(),
                    edtNomUser.text.toString().trim(),
                    edtPseudoUser.text.toString().trim(),
                    edtPasswordUser.text.toString().trim(),
                    role.idRole
                )

                enregViewModel.saveUser(user) {
                    view.edtNomUser.text.clear()
                    view.edtPseudoUser.text.clear()
                    view.edtPasswordUser.text.clear()
                    view.edtPasswordConfirmUser.text.clear()

                    toggleSection(view.btnToggleUser, view.lytExpandUser)
                    context?.toast("Success")
                }

                view.snackbar("Success")
                toggleSection(view.btnToggleUser, view.lytExpandUser)
            }
        }
    }

    private fun formValidation(view: View): Boolean {
        return when {

            view.edtNomUser.text.isBlank() -> {
                view.edtNomUser.error = getString(R.string.input_empty)
                false
            }

            view.edtPseudoUser.text.isBlank() -> {
                view.edtPseudoUser.error = getString(R.string.input_empty)
                false
            }

            view.edtPasswordUser.text.isBlank() -> {
                view.edtPasswordUser.error = getString(R.string.input_empty)
                false
            }

            view.edtPasswordConfirmUser.text.toString() != view.edtPasswordUser.text.toString() -> {
                view.edtPasswordUser.error = getString(R.string.password_wrong)
                false
            }

            else -> true
        }
    }

    private fun showRoleDialog(view: View, data: List<Role>) {

        val array = data.asSequence().map { it.nomRole }.toList().toTypedArray()

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Role")
        builder.setSingleChoiceItems(array, -1) { dialogInterface, i ->

            (view as EditText).setText(array[i])
            dialogInterface.dismiss()
        }

        builder.show()
    }

    private fun dialogDatePickerLight(view: EditText) {
        val currentCalender = Calendar.getInstance()

        val datePicker = DatePickerDialog.newInstance(
            { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }

                val date = calendar.timeInMillis
                view.setText(Tools.getFormattedDateShort(date))
            },

            currentCalender.get(Calendar.YEAR),
            currentCalender.get(Calendar.MONTH),
            currentCalender.get(Calendar.DAY_OF_MONTH)
        )

        //set dark light
        datePicker.run {
            isThemeDark = false
            accentColor = resources.getColor(R.color.colorPrimary)
            minDate = currentCalender
            show(activity?.fragmentManager, "tag")
        }
    }

    private fun initAgencyComponent(view: View) {
        view.btnToggleAgency.setOnClickListener {
            toggleSection(it, view.lytExpandAgency)
        }

        view.btnHideAgency.setOnClickListener {
            toggleSection(view.btnToggleAgency, view.lytExpandAgency)
        }

        view.btnEnregAgency.setOnClickListener {
            if (view.edtNomAgency.text.isBlank()) {
                view.edtNomAgency.error = getString(R.string.input_empty)
            } else {
                val agency = Agence(System.nanoTime(), edtNomAgency.text.toString().trim())
                enregViewModel.saveAgence(agency) {
                    view.edtNomAgency.text.clear()
                    toggleSection(view.btnToggleAgency, view.lytExpandAgency)
                    context?.toast("Success")
                }
            }
        }
    }

    private fun initEtatComponent(view: View) {
        view.btnToggleEtat.setOnClickListener {
            toggleSection(it, view.lytExpandEtat)
        }

        view.btnHideEtat.setOnClickListener {
            toggleSection(view.btnToggleEtat, view.lytExpandEtat)
        }

        view.btnEnregEtat.setOnClickListener {
            if (view.edtNomEtat.text.isBlank()) {
                view.edtNomEtat.error = getString(R.string.input_empty)
            } else {
                val etat = Etat(System.nanoTime(), edtNomEtat.text.toString().trim())
                enregViewModel.saveEtat(etat) {
                    view.edtNomEtat.text.clear()
                    context?.toast("Success")
                    toggleSection(view.btnToggleEtat, view.lytExpandEtat)
                }
            }
        }
    }

    private fun initLieuComponent(view: View) {
        view.btnToggleLieu.setOnClickListener {
            toggleSection(it, view.lytExpandLieu)
        }

        view.btnHideLieu.setOnClickListener {
            toggleSection(view.btnToggleLieu, view.lytExpandLieu)
        }

        view.btnEnregLieu.setOnClickListener {
            if (view.edtNomLieu.text.isBlank()) {
                view.edtNomLieu.error = getString(R.string.input_empty)
            } else {
                val lieu = Lieu(System.nanoTime(), edtNomLieu.text.toString().trim())
                enregViewModel.saveLieu(lieu) {
                    view.edtNomLieu.text.clear()
                    toggleSection(view.btnToggleLieu, view.lytExpandLieu)
                    context?.toast("Success")
                }
            }
        }
    }

    private fun initRoleComponent(view: View) {
        view.btnToggleRole.setOnClickListener {
            toggleSection(it, view.lytExpandRole)
        }

        view.btnHideRole.setOnClickListener {
            toggleSection(view.btnToggleRole, view.lytExpandRole)
        }

        view.btnEnregRole.setOnClickListener {
            if (view.edtNomRole.text.isBlank()) {
                view.edtNomRole.error = getString(R.string.input_empty)
            } else {
                val role = Role(System.nanoTime(), edtNomRole.text.toString().trim())
                enregViewModel.saveRole(role) {
                    view.edtNomRole.text.clear()
                    toggleSection(view.btnToggleRole, view.lytExpandRole)
                    context?.toast("Success")
                }
            }
        }
    }

    private fun initTransitComponent(view: View) {
        view.btnToggleTransit.setOnClickListener {
            toggleSection(it, view.lytExpandTransit)
        }

        view.btnHideTransit.setOnClickListener {
            toggleSection(view.btnToggleTransit, view.lytExpandTransit)
        }

        view.btnEnregTransit.setOnClickListener {
            if (view.edtNomTransit.text.isBlank()) {
                view.edtNomTransit.error = getString(R.string.input_empty)
            } else {
                val transit = Transit(System.nanoTime(), edtNomTransit.text.toString().trim())
                enregViewModel.saveTransit(transit) {
                    view.edtNomTransit.text.clear()
                    toggleSection(view.btnToggleTransit, view.lytExpandTransit)
                    context?.toast("Success")
                }
            }
        }
    }

    // The [view] represent the toggle and [layout] the layout that will be hided
    private fun toggleSection(view: View, layout: LinearLayout) {

        val show = toggleArrow(view)

        if (show) {
            ViewAnimation.expand(layout, object : ViewAnimation.AnimListener {
                override fun onFinish() {
                    Tools.nestedScrollTo(nestedScrollView, layout)
                }
            })

        } else {
            ViewAnimation.collapse(layout)
        }
    }

    private fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }
}
