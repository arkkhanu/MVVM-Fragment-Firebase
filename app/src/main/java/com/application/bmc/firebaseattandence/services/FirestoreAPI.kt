package com.application.bmc.firebaseattandence.services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface FirestoreAPI {
    companion object {
        internal fun getFirestoreInstance(): FirebaseFirestore {
            return Firebase.firestore
        }
    }
}