package com.application.bmc.firebaseattandence.ui

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.application.bmc.firebaseattandence.R
import com.application.bmc.firebaseattandence.ui.Fragments.LoginFragment
import com.application.bmc.firebaseattandence.ui.Fragments.SignUpFragment
import com.application.bmc.firebaseattandence.viewmodels.FirebaseAuth_ViewModel

class AuthActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var btn_decide_signUp : Button
    lateinit var btn_decide_signIn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initializeIds()


        btn_decide_signIn.setOnClickListener {
            btn_decide_signUp.setBackgroundColor(Color.TRANSPARENT)
            btn_decide_signIn.setBackgroundColor(ContextCompat.getColor(this,R.color.purple_700))

            btn_decide_signUp.setTextColor(ContextCompat.getColor(this,R.color.black))
            btn_decide_signIn.setTextColor(ContextCompat.getColor(this,R.color.white))
            initializeFragmentsObject()
            fragmentTransaction.replace(R.id.fragment_container_auth,LoginFragment()).apply {
                this.commit()
            }

        }
        btn_decide_signUp.setOnClickListener {
            btn_decide_signIn.setBackgroundColor(Color.TRANSPARENT)
            btn_decide_signUp.setBackgroundColor(ContextCompat.getColor(this,R.color.purple_700))
            btn_decide_signUp.setTextColor(ContextCompat.getColor(this,R.color.white))
            btn_decide_signIn.setTextColor(ContextCompat.getColor(this,R.color.black))
            initializeFragmentsObject()
            fragmentTransaction.replace(R.id.fragment_container_auth,SignUpFragment()).apply {
                this.commit()
            }
        }
    }

    fun initializeIds(){
        btn_decide_signIn = findViewById(R.id.btn_decide_signIn)
        btn_decide_signUp = findViewById(R.id.btn_decide_signUp)
        initializeFragmentsObject()
        fragmentTransaction.add(R.id.fragment_container_auth,LoginFragment()).apply {
            this.commit()
        }
    }

    fun initializeFragmentsObject(){
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
    }
}