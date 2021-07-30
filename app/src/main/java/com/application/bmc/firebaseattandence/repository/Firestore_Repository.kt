package com.application.bmc.firebaseattandence.repository

import com.application.bmc.firebaseattandence.services.FirestoreAPI
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

class Firestore_Repository : FirestoreFunctions {
    private val firestoreInstance = FirestoreAPI

    override fun writeIntoUserCollection() : CollectionReference {
      return  firestoreInstance.getFirestoreInstance().collection("user")
    }

    override fun readFromUserCollection() : CollectionReference{
      return  firestoreInstance.getFirestoreInstance().collection("user")
    }

    override fun getDocumentReference(): DocumentReference {
      return  firestoreInstance.getFirestoreInstance().collection("user").document()
    }

}

interface FirestoreFunctions{
    fun writeIntoUserCollection() : CollectionReference
    fun readFromUserCollection() : CollectionReference
    fun getDocumentReference() : DocumentReference
}




