package com.application.bmc.firebaseattandence.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthAPI {

    companion object{
        private var firebaseAuth : FirebaseAuth? = null
       internal fun getInstance() : FirebaseAuth{
            if (firebaseAuth == null){
                firebaseAuth = FirebaseAuth.getInstance()
            }
            return firebaseAuth!!
        }
    }
}




