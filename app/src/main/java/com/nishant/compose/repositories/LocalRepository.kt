package com.nishant.compose.repositories

import java.text.SimpleDateFormat
import java.util.*


class LocalRepository {

    fun getDelhiTime(): IntArray {
        val df = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        df.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        return df.format(Date()).split(":").map { it.toInt() }.toIntArray()
    }

    fun getLondonTime(): IntArray {
        val df = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        df.timeZone = TimeZone.getTimeZone("Europe/London")
        return df.format(Date()).split(":").map { it.toInt() }.toIntArray()
    }

    fun getTokyoTime(): IntArray {
        val df = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        df.timeZone = TimeZone.getTimeZone("Europe/Berlin")
        return df.format(Date()).split(":").map { it.toInt() }.toIntArray()
    }

    fun getNewYorkTime(): IntArray {
        val df = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        df.timeZone = TimeZone.getTimeZone("America/New_York")
        return df.format(Date()).split(":").map { it.toInt() }.toIntArray()
    }
}