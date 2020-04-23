package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbycoord

import com.google.gson.annotations.SerializedName

class Main {
    @SerializedName("feels_like")
    var feelsLike: Double? = null
    @SerializedName("humidity")
    var humidity: Long? = null
    @SerializedName("pressure")
    var pressure: Long? = null
    @SerializedName("temp")
    var temp: Double? = null
    @SerializedName("temp_max")
    var tempMax: Double? = null
    @SerializedName("temp_min")
    var tempMin: Double? = null

}