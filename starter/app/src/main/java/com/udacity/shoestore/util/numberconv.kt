package com.udacity.shoestore.util

import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.icu.text.DecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun doubleToString(value: Double, decimals: Int = 1): String =
    DecimalFormat.getInstance().run {
        maximumFractionDigits = decimals
        format(value)
    }

@RequiresApi(Build.VERSION_CODES.N)
fun stringToDouble(value: String) =
    DecimalFormat.getInstance().parse(value).toDouble()

