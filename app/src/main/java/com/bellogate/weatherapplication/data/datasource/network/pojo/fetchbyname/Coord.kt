package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname

import com.google.gson.annotations.SerializedName

class Coord {
    @SerializedName("lat")
    var lat: Double? = null
    @SerializedName("lon")
    var lon: Double? = null

}