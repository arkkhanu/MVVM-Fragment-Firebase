package com.application.bmc.firebaseattandence.core

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.application.bmc.firebaseattandence.R

internal class CustomAlertProgressDialog(activity: Activity?) : DialogFunction {
    private var alertDialogProgressBar: AlertDialog? = null

    init {
        alertDialogProgressBar = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = activity.layoutInflater
            builder.setView(inflater.inflate(R.layout.progress_view, null)).apply {
                setCancelable(false)
            }

            builder.create()
        }
    }

    override fun showProgressBar() {
        alertDialogProgressBar?.show()
    }

    override fun dissmissProgressBar() {
        alertDialogProgressBar?.dismiss()
    }


}

 interface DialogFunction {

    fun showProgressBar()
    fun dissmissProgressBar()
}
