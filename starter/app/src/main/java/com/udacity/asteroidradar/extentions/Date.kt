package com.udacity.asteroidradar.extentions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return format.format(this)
}