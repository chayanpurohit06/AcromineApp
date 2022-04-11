package com.app.acromine.Util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.util.regex.Pattern

class Utils {

    companion object{

        val EMAIL_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun isSfsOrIfsEmail(sfs: CharSequence?): Boolean {
            return EMAIL_PATTERN.matcher(sfs).matches()
        }

        fun isSfsOrIfsNullorBlank(sfs: CharSequence?): Boolean {
            return sfs.isNullOrEmpty() && sfs.isNullOrBlank()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }


       fun showDialog(activity:Activity,text : String){

           val builder = AlertDialog.Builder(activity)
           builder.setTitle("Error")
           builder.setMessage(text)
           builder.setIcon(android.R.drawable.ic_dialog_alert)
           builder.setPositiveButton("OK"){dialogInterface, which -> dialogInterface.dismiss() }
           val alertDialog: AlertDialog = builder.create()
           alertDialog.setCancelable(false)
           alertDialog.show()
       }


    }

}