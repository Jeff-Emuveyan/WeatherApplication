package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname

import com.google.gson.annotations.SerializedName

class FiveDayWeatherForecast {
    @SerializedName("city")
    var city: City? = null
    @SerializedName("cnt")
    var cnt: Long? = null
    @SerializedName("cod")
    var cod: String? = null
    @SerializedName("list")
    var list: kotlin.collections.List<List>? =
        null
    @SerializedName("message")
    var message: Long? = null

}