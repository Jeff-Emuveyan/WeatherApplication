package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname

import com.google.gson.annotations.SerializedName

class Wind {
    @SerializedName("deg")
    var deg: Long? = null
    @SerializedName("speed")
    var speed: Double? = null

}