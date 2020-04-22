package com.bellogate.weatherapplication.data.datasource.network.pojo

import com.google.gson.annotations.SerializedName

class Weather {
    @SerializedName("description")
    var description: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("id")
    var id: Long? = null
    @SerializedName("main")
    var main: String? = null

}