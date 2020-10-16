package project.dheeraj.newsup2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage

/**
 * Created by Dheeraj Kotwani on 16-10-2020.
 */
class SmsReceiver : BroadcastReceiver() {
    var otp: String? = null
    override fun onReceive(context: Context?, intent: Intent) {
        val data = intent.extras
        val pdus = data!!["pdus"] as Array<Any>?
        for (i in pdus!!.indices) {
            val smsMessage = SmsMessage.createFromPdu(
                pdus[i] as ByteArray
            )
            val sender = smsMessage.displayOriginatingAddress
            val messageBody = smsMessage.messageBody
            otp = messageBody.replace("[^0-9]".toRegex(), "")
            mListener?.onMessagereceived(otp!!)
        }
    }

    companion object {
        private var mListener: SmsListener? = null
        fun bindListener(listener: SmsListener?) {
            mListener = listener
        }
    }
}