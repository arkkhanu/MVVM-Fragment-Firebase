package com.application.bmc.firebaseattandence.ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.application.bmc.firebaseattandence.R
import com.application.bmc.firebaseattandence.core.CustomAlertProgressDialog
import com.application.bmc.firebaseattandence.core.DialogFunction
import com.application.bmc.firebaseattandence.core.Validation
import com.application.bmc.firebaseattandence.model.User
import com.application.bmc.firebaseattandence.viewmodels.AuthStatus
import com.application.bmc.firebaseattandence.viewmodels.FirebaseAuth_ViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.cancel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rootView : View
    private lateinit var et_username : TextView
    private lateinit var et_password : TextView
    private lateinit var btn_login:Button
    val TAG :String= "Ark"

//    private val firebaseauthViewmodel : FirebaseAuth_ViewModel by activityViewModels()
    private lateinit var firebaseauthViewmodel : FirebaseAuth_ViewModel

    private lateinit var dialogFunction: DialogFunction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseauthViewmodel = ViewModelProvider(this).get(FirebaseAuth_ViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView =  inflater.inflate(R.layout.fragment_login, container, false)

        return  rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeIds()
        loginBtnClick()
//        firebaseauthViewmodel.responselogin.removeObservers(viewLifecycleOwner)
        firebaseauthViewmodel.responselogin.observe(viewLifecycleOwner, Observer {
            when(it.status){
                AuthStatus.LOADING -> {
                    val i= 0
                    Log.d(TAG,"server is Loading.... ${it.message.toString()}")
                    dialogFunction.showProgressBar()
                }
                AuthStatus.SUCCESS -> {
                    val i= 0
                    Log.d(TAG,"server is Success.... ${it.message.toString()}")
                    dialogFunction.dissmissProgressBar()
                    Snackbar.make(requireActivity().findViewById(R.id.fragment_container_auth),it.message.toString(),Snackbar.LENGTH_LONG).show()
//                    Snackbar.make(view,it.message.toString(),Snackbar.LENGTH_LONG).show()
                }
                AuthStatus.ERROR -> {
                    val i= 0
                    Log.d(TAG,"server is Error.... ${it.message.toString()}")
                    dialogFunction.dissmissProgressBar()
                    Snackbar.make(requireActivity().findViewById(R.id.fragment_container_auth),it.message.toString(),Snackbar.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun initializeIds() {
        et_password = rootView.findViewById(R.id.et_password_logIn)
        et_username = rootView.findViewById(R.id.et_username_logIn)
        btn_login = rootView.findViewById(R.id.btn_login_logIn)
        dialogFunction = CustomAlertProgressDialog(requireActivity())
        dialogFunction = CustomAlertProgressDialog(requireActivity())

    }

    private fun loginBtnClick(){
        btn_login.setOnClickListener {
            if(checkValidation()){
                   firebaseauthViewmodel.signIn(et_username.text.toString(),et_password.text.toString())
            }
        }
    }

    private fun checkValidation(): Boolean {
        Validation().ValidationFields("", "")
        return Validation.checkValidation(
            requireActivity(),
            arrayListOf(
                Validation().ValidationFields(et_username.text.toString(), "Please Enter User Name"),
                Validation().ValidationFields(et_password.text.toString(), "Please Enter Password"),
            )
        )
    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}