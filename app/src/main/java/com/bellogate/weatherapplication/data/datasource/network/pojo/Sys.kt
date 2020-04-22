package com.bellogate.weatherapplication.data.datasource.network.pojo

import com.google.gson.annotations.SerializedName
class Sys {
    @SerializedName("country")
    var country: String? = null
    @SerializedName("id")
    var id: Long? = null
    @SerializedName("sunrise")
    var sunrise: Long? = null
    @SerializedName("sunset")
    var sunset: Long? = null
    @SerializedName("type")
    var type: Long? = null

}