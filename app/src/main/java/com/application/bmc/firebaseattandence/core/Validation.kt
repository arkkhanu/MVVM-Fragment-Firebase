package com.application.bmc.firebaseattandence.core

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.application.bmc.firebaseattandence.R
import com.google.android.material.snackbar.Snackbar

class Validation {

    companion object{
        fun checkValidation(context: Activity,list: List<ValidationFields>):Boolean{
            for(i in list){
                if(checkNullOrEmpty(i.value)){
                    Toast.makeText(context.applicationContext,i.message,Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "checkValidation: ")
                    return false
                }
            }
            return true
        }
        private fun checkNullOrEmpty( _value:String?):Boolean{
            return _value.isNullOrEmpty() || _value.isEmpty() || _value.isBlank()
        }
    }



inner   class ValidationFields(val value:String,val message:String)


}

