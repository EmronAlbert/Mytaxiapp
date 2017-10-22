package com.mytaxiapp.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author Tosin Onikute.
 */

object NetworkUtil {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        if (ni == null) {
            // There are no active networks.
            return false
        } else {
            return true
        }
    }


}
