package com.bellogate.weatherapplication.ui.util

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat


const val USER_CURRENT_CITY = "userCurrentCity"

fun AppCompatActivity.showAlert(message: String){
    AlertDialog.Builder(this).setTitle("Weather application")
        .setMessage(message).show()
}


fun convertToCelsius(kelvin: Double): Double{
    val celsious = kelvin - 273.15
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(celsious).toDouble()
}
