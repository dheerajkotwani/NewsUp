package project.dheeraj.newsup2.Util

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import project.dheeraj.newsup2.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



object UtilMethods{

    fun isInternetAvailable(context : Context) : Boolean{

        val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (conMgr!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED || conMgr.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            )!!.state == NetworkInfo.State.CONNECTED
        ) {

            return true
        } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.DISCONNECTED || conMgr.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            )!!.state == NetworkInfo.State.DISCONNECTED
        ) {

            return false
        }

        return true
    }

    fun convertISOTime(context: Context, isoTime: String?): String? {

        val sdf = SimpleDateFormat(context.getString(R.string.default_time_format))
        var convertedDate: Date? = null
        var formattedDate: String? = null
        var formattedTime: String = "10:00 AM"
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat(context.getString(R.string.date_format)).format(convertedDate)
            formattedTime = SimpleDateFormat(context.getString(R.string.time_format)).format(convertedDate)

            if((formattedTime.subSequence(6,8).toString().equals("PM") || formattedTime.subSequence(6,8).toString().equals("pm")) && formattedTime.subSequence(0,2).toString().toInt()>12){
                formattedTime = (formattedTime.subSequence(0,2).toString().toInt()-12).toString()+formattedTime.subSequence(2,8).toString()
            }
            if (formattedTime.subSequence(0,2).toString().equals("00")){
                formattedTime = (formattedTime.subSequence(0,2).toString().toInt()+1).toString()+formattedTime.subSequence(2,8).toString()

            }
            if (formattedTime.subSequence(0,2).toString().equals("0:")){
                formattedTime = (formattedTime.subSequence(0,1).toString().toInt()+1).toString()+formattedTime.subSequence(2,8).toString()

            }


            Log.d("Date ", "$formattedDate | $formattedTime")
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("Error Date ", e.message)
        }
        return formattedDate+" | "+formattedTime
    }

    fun showLoader(context: Context, title: String, message: String){

        val dialog = ProgressDialog(context)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.show()

    }

    fun DismissLoader(){
    }

}