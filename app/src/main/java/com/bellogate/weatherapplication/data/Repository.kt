package com.bellogate.weatherapplication.data

import com.bellogate.weatherapplication.data.datasource.network.EndPoints
import com.bellogate.weatherapplication.data.datasource.network.NetworkAccess

class Repository {

    private var endPoints: EndPoints = NetworkAccess.getClient().create(EndPoints::class.java)

    suspend fun fetchWeatherForecastByCoordinates(appid: String = "670302b36d362be3a9b1d86d7f2cb1ab",
                                                  latitude: Double,
                                                  longitude: Double)
            = endPoints.fetchWeatherForecastByCoordinates(latitude.toString(),
            longitude.toString(), appid)

}