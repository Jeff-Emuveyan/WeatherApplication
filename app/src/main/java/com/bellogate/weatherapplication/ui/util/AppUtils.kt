package com.bellogate.weatherapplication.ui.util

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bellogate.weatherapplication.R
import com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname.FiveDayWeatherForecast
import java.math.RoundingMode
import java.text.DecimalFormat


const val USER_CURRENT_CITY = "userCurrentCity"

fun AppCompatActivity.showAlert(message: String){
    AlertDialog.Builder(this).setTitle("Weather application")
        .setMessage(message).show()
}

fun showAlert(context: Context, message: String){
    AlertDialog.Builder(context).setTitle("Weather application")
        .setMessage(message).show()
}

fun convertToCelsius(kelvin: Double): Double{
    val celsious = kelvin - 273.15
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(celsious).toDouble()
}


enum class Type{
    BY_COORDINATES,
    BY_NAME
}

fun getFiveDayForecast(fiveDayWeatherForecast: FiveDayWeatherForecast): String{

    var forecast: String = ""
    var year: String = ""
    var count = 1
    for(list in fiveDayWeatherForecast.list!!){

        if(list.year != year){
            year = list.year!!
            forecast += "\n\n---- Day $count ----"
            ++count
        }

        forecast = "$forecast  \n${list.dtTxt}  :  ${convertToCelsius(list.main?.temp!!)}°C".trim()
    }
    return forecast
}
