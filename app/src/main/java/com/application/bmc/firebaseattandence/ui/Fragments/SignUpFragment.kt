package com.application.bmc.firebaseattandence.ui.Fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.application.bmc.firebaseattandence.R
import com.application.bmc.firebaseattandence.core.CustomAlertProgressDialog
import com.application.bmc.firebaseattandence.core.DialogFunction
import com.application.bmc.firebaseattandence.core.Validation
import com.application.bmc.firebaseattandence.model.User
import com.application.bmc.firebaseattandence.viewmodels.AuthStatus
import com.application.bmc.firebaseattandence.viewmodels.FirebaseAuth_ViewModel
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignUpFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rootView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var et_username: TextView
    private lateinit var et_password: TextView
    private lateinit var et_age: TextView
    private lateinit var gender_group: RadioGroup
    private var gender: String = "Male"
    private lateinit var btn_signup: Button
//    private val firebaseAuth_ViewModel: FirebaseAuth_ViewModel by activityViewModels()
    private lateinit var firebaseAuth_ViewModel: FirebaseAuth_ViewModel
    val TAG: String = "Ark"
    private var _user: User? = null
    private lateinit var dialogFunction: DialogFunction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth_ViewModel = ViewModelProvider(this).get(FirebaseAuth_ViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rootView = inflater.inflate(R.layout.fragment_sign_up, container, false)

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initializeIds()
        genderSelection()
        signUpBtnClick()

        firebaseAuth_ViewModel.responseAuth.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                AuthStatus.LOADING -> {
                    val i = 0
                    Log.d(TAG, "server is Loading.... ${it.message.toString()}")
                    dialogFunction.showProgressBar()
                }
                AuthStatus.SUCCESS -> {
                    val i = 0
                    Log.d(TAG, "server is Success.... ${it.message.toString()}")
                    dialogFunction.dissmissProgressBar()
//                    Snackbar.make(rootView, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                AuthStatus.ERROR -> {
                    val i = 0
                    Log.d(TAG, "server is Error.... ${it.message.toString()}")
                    dialogFunction.dissmissProgressBar()
//                    Snackbar.make(rootView, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })

        firebaseAuth_ViewModel.responseFirestore.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                AuthStatus.LOADING -> {
                    val i = 0
                    Log.d(TAG, "server is Loading.... ${it.message.toString()}")
                    dialogFunction.showProgressBar()
                }
                AuthStatus.SUCCESS -> {
                    val i = 0
                    Log.d(TAG, "server is Success.... ${it.message.toString()}")
                    dialogFunction.dissmissProgressBar()
//                    Snackbar.make(rootView, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                AuthStatus.ERROR -> {
                    val i = 0
                    Log.d(TAG, "server is Error.... ${it.message.toString()}")
                    dialogFunction.dissmissProgressBar()
//                    Snackbar.make(rootView, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun initializeIds() {
        et_password = rootView.findViewById(R.id.et_password_signUp)
        et_username = rootView.findViewById(R.id.et_username_signUp)
        et_age = rootView.findViewById(R.id.et_age_signUp)
        gender_group = rootView.findViewById(R.id.gender_signUp)
        btn_signup = rootView.findViewById(R.id.btn_signup_signUp)
        progressBar = rootView.findViewById(R.id.progress_circular)
        dialogFunction = CustomAlertProgressDialog(requireActivity())

    }

    private fun genderSelection() {
        gender_group.setOnCheckedChangeListener { group, checkedId ->
            gender = when (checkedId) {
                R.id.gender_male_signUp -> "Male"
                R.id.gender_female_signUp -> "Female"
                else -> "Male"
            }
        }
    }

    private fun signUpBtnClick() {
        btn_signup.setOnClickListener {
            if (checkValidation()) {
                _user = User(
                    null,
                    et_username.text.toString(),
                    et_password.text.toString(),
                    gender,
                    et_age.text.toString()
                )
                firebaseAuth_ViewModel.signUp(user = _user!!)
            }
        }
    }


    private fun checkValidation(): Boolean {
        Validation().ValidationFields("", "")
        return Validation.checkValidation(
            requireActivity(),
            arrayListOf(
                Validation().ValidationFields(
                    et_username.text.toString(),
                    "Please Enter User Name"
                ),
                Validation().ValidationFields(et_password.text.toString(), "Please Enter Password"),
                Validation().ValidationFields(et_age.text.toString(), "Please Enter Age")
            )
        )
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}