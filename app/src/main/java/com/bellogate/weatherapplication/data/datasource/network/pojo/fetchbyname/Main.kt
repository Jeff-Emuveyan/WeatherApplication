package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname

import com.google.gson.annotations.SerializedName

class Main {
    @SerializedName("feels_like")
    var feelsLike: Double? = null
    @SerializedName("grnd_level")
    var grndLevel: Long? = null
    @SerializedName("humidity")
    var humidity: Long? = null
    @SerializedName("pressure")
    var pressure: Long? = null
    @SerializedName("sea_level")
    var seaLevel: Long? = null
    @SerializedName("temp")
    var temp: Double? = null
    @SerializedName("temp_kf")
    var tempKf: Double? = null
    @SerializedName("temp_max")
    var tempMax: Double? = null
    @SerializedName("temp_min")
    var tempMin: Double? = null

}