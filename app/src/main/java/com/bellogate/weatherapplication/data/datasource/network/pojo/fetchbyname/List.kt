package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname

import com.google.gson.annotations.SerializedName

class List {
    @SerializedName("clouds")
    var clouds: Clouds? =
        null
    @SerializedName("dt")
    var dt: Long? = null
    @SerializedName("dt_txt")
    var dtTxt: String? = null
    @SerializedName("main")
    var main: Main? =
        null
    @SerializedName("sys")
    var sys: Sys? =
        null
    @SerializedName("weather")
    var weather: java.util.List<Weather>? =
        null
    @SerializedName("wind")
    var wind: Wind? =
        null

}