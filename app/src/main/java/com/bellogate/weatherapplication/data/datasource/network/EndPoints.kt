package com.bellogate.weatherapplication.data.datasource.network

import com.bellogate.weatherapplication.data.datasource.network.pojo.WeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoints {

    @GET("/data/2.5/weather")
    suspend fun fetchWeatherForecastByCoordinates(@Query("lat")latitude: String,
                                                  @Query("lon")longitude: String,
                                                  @Query("appid")appid: String): Response<WeatherForecastResponse>
}