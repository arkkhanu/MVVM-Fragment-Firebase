package com.application.bmc.firebaseattandence.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.bmc.firebaseattandence.model.User
import com.application.bmc.firebaseattandence.repository.FirebaseAuth_Repository
import com.application.bmc.firebaseattandence.repository.Firestore_Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirebaseAuth_ViewModel : ViewModel() {

    val firebaseauthRepository = FirebaseAuth_Repository()
    val firestoreRepository = Firestore_Repository()
    val responseAuth = MutableLiveData<ResponseAuth<Any>>()
    val responselogin = MutableLiveData<ResponseAuth<Any>>()
    val responseFirestore = MutableLiveData<ResponseAuth<Any>>()
    val TAG = "Ark"
    fun signUp(user: User) {
        responseAuth.postValue(ResponseAuth(AuthStatus.LOADING, null, "Loading"))
        try {
            firebaseauthRepository.signUp(user.username!!, user.password!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userUid = firebaseauthRepository.getCurrentUser()?.uid
                    user.uid = userUid
                    writeIntoUser(user)
                    responseAuth.postValue(ResponseAuth(AuthStatus.SUCCESS, user, "Successful"))
                } else {
                    responseAuth.postValue(
                        ResponseAuth(AuthStatus.ERROR, null, "Failed ${it.exception?.message}")
                    )
                }
            }

        } catch (e: Exception) {
            responseAuth.postValue(
                ResponseAuth(
                    AuthStatus.ERROR,
                    null,
                    "Fail ${e.message?.toString()}"
                )
            )
        }

    }

    fun signIn(username: String, password: String) {
        responselogin.postValue(ResponseAuth(AuthStatus.LOADING, null, "Loading"))
        try {
            viewModelScope.launch {
//                delay(4000)
                firebaseauthRepository.signIn(username, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = firebaseauthRepository.getCurrentUser()?.email
                        responselogin.postValue(ResponseAuth(AuthStatus.SUCCESS, user, "Successful"))
                    } else {
                        responselogin.postValue(
                            ResponseAuth(
                                AuthStatus.ERROR,
                                null,
                                "Failed ${it.exception}"
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            responselogin.postValue(ResponseAuth(AuthStatus.ERROR, null, "Fail ${e.message}"))
        }
    }

    fun writeIntoUser(user: User) {
        responseAuth.postValue(ResponseAuth(AuthStatus.LOADING, null, "Loading"))
        try {

            firestoreRepository.writeIntoUserCollection().document(user.uid!!).set(user)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val a = firestoreRepository.getDocumentReference()
                        Log.d(TAG, "DocR: $a")
                        Log.d(TAG, "GET: ${a.get()}")
                        Log.d(TAG, "ID: ${a.id}")
                        Log.d(TAG, "Path: ${a.path}")

                        responseAuth.postValue(ResponseAuth(AuthStatus.SUCCESS, null, "Success"))
                    } else {
                        responseAuth.postValue(
                            ResponseAuth(
                                AuthStatus.ERROR,
                                null,
                                "Failed ${it.exception}"
                            )
                        )
                    }
                }
        } catch (e: Exception) {
            responseAuth.postValue(ResponseAuth(AuthStatus.ERROR, null, "Fail ${e.message}"))
        }

    }
}


enum class AuthStatus {
    SUCCESS,
    ERROR,
    LOADING
}
enum class Auth {
    LOGIN,
    SIGNUP
}


data class ResponseAuth<T>(val status: AuthStatus, val data: T?, val message: String)
