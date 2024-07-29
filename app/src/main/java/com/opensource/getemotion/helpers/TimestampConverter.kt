package com.opensource.getemotion.helpers

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


class TimestampConverter {

    companion object{
        fun toTimeStamp(time : Long) : Timestamp {
            return Timestamp(time / 1000, 0);

        }

        @SuppressLint("SimpleDateFormat")
        fun toDateTime(timestamp: Timestamp) : String {
            val sdf = SimpleDateFormat("MM/dd/yyyy - HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("Asia/Singapore")
            return sdf.format(timestamp.seconds.times(1000L))
        }

        @SuppressLint("SimpleDateFormat")
        fun toDateTime(timestamp: Long) : String {
            val time = Timestamp(timestamp / 1000, 0)
            val sdf = SimpleDateFormat("MM/dd/yyyy - HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("Asia/Singapore")
            return sdf.format(time.seconds.times(1000L))
        }

    }


}