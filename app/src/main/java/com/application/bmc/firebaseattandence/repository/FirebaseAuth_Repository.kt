package com.application.bmc.firebaseattandence.repository

import com.application.bmc.firebaseattandence.services.FirebaseAuthAPI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class FirebaseAuth_Repository : FirebaseAuthFunction {

    private val authAPI = FirebaseAuthAPI.getInstance()
    override fun signUp(username: String, password: String): Task<AuthResult> {
        return authAPI.createUserWithEmailAndPassword(username, password)
    }

    override fun signIn(username: String, password: String): Task<AuthResult> {
        return authAPI.signInWithEmailAndPassword(username, password)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authAPI.currentUser
    }
}

interface FirebaseAuthFunction {
    fun signUp(username: String, password: String): Task<AuthResult>
    fun signIn(username: String, password: String): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
}


/*
//data class Resourse<out T>(val status: Status?, val data: T?, val message: String?) {
//    fun <T> success(data: T): Resourse<T> = Resourse(status = Status.SUCCESS, data = data, message = null)
//
//    fun <T> error(data: T?, message: String): Resourse<T> = Resourse(status = Status.ERROR, data = data, message = message)
//
//    fun <T> loading(): Resourse<T> = Resourse(status = Status.LOADING, data = null, message = null)
//    fun <T> loading(data: T?): Resourse<T> = Resourse(status = Status.LOADING, data = data, message = null)
//}

//data class ResultF<T>(val data: T?, val message: String?, val loading: Boolean)
//sealed class ResultFetched<out T:Any>{
//    data class onResponse<out T:Any>(val data: T?,val message:String?,val loading:Boolean):ResultFetched<T>()
//    data class onSuccess<out T:Any>(val data: T):ResultFetched<T>()
//    data class onFailure(val message:String?):ResultFetched<String>()
//    data class onLoading(val loading:Boolean):ResultFetched<Boolean>()
//    data class Success(val data:String):ResultFetched()
//    data class Failure(val data:String):ResultFetched()
//}*/
