package com.bellogate.weatherapplication.data.datasource.network.pojo

import com.google.gson.annotations.SerializedName

class Coord {
    @SerializedName("lat")
    var lat: Double? = null
    @SerializedName("lon")
    var lon: Double? = null

}