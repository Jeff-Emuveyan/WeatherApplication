package com.bellogate.weatherapplication.data.datasource.network.pojo.fetchbyname

import com.google.gson.annotations.SerializedName

class City {
    @SerializedName("coord")
    var coord: Coord? =
        null
    @SerializedName("country")
    var country: String? = null
    @SerializedName("id")
    var id: Long? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("population")
    var population: Long? = null
    @SerializedName("sunrise")
    var sunrise: Long? = null
    @SerializedName("sunset")
    var sunset: Long? = null
    @SerializedName("timezone")
    var timezone: Long? = null

}