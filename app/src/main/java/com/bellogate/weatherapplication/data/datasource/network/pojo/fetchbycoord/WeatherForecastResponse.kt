package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbycoord

import com.bellogate.weatherapplication.data.datasource.network.pojo.Wind
import com.google.gson.annotations.SerializedName

class WeatherForecastResponse {
    @SerializedName("base")
    var base: String? = null
    @SerializedName("clouds")
    var clouds: Clouds? = null
    @SerializedName("cod")
    var cod: Long? = null
    @SerializedName("coord")
    var coord: Coord? = null
    @SerializedName("dt")
    var dt: Long? = null
    @SerializedName("id")
    var id: Long? = null
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("sys")
    var sys: Sys? = null
    @SerializedName("timezone")
    var timezone: Long? = null
    @SerializedName("visibility")
    var visibility: Long? = null
    @SerializedName("weather")
    var weather: List<Weather>? = null
    @SerializedName("wind")
    var wind: Wind? = null

}