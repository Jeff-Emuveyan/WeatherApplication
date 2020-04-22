package com.bellogate.weatherapplication.data.datasource.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkAccess {

    fun getClient(): Retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}