package com.bellogate.weatherapplication.data

import android.content.Context
import com.bellogate.weatherapplication.data.datasource.database.AppDatabase
import com.bellogate.weatherapplication.data.datasource.database.pojo.WeatherForecast
import com.bellogate.weatherapplication.data.datasource.network.EndPoints
import com.bellogate.weatherapplication.data.datasource.network.NetworkAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(context: Context) {

    private val db = AppDatabase.getDatabase(context)

    private var endPoints: EndPoints = NetworkAccess.getClient().create(EndPoints::class.java)


    /**
     * Fetch weather forecasts from the API using coordinates
     */
    suspend fun fetchWeatherForecastByCoordinates(appid: String = "670302b36d362be3a9b1d86d7f2cb1ab",
                                                  latitude: Double,
                                                  longitude: Double)
            = endPoints.fetchWeatherForecastByCoordinates(latitude.toString(),
            longitude.toString(), appid)


    /**
     * Searches DB from weather forecast using 'name'
     */
    suspend fun fetchWeatherForecastByNameFromDB(cityName: String) =
        db.weatherForecastDao().getWeatherForecastSynchronously(cityName)


    /**
     * Save weather forecast to db
     ***/
    fun saveWeatherForecast(coroutineScope: CoroutineScope, weatherForecast: WeatherForecast){

        coroutineScope.launch {

            withContext(Dispatchers.IO){

                weatherForecast.cityName?.let {cityName ->
                    val oldWeatherForecast = db.weatherForecastDao().getWeatherForecastSynchronously(cityName)

                    if(oldWeatherForecast == null){
                        db.weatherForecastDao().saveWeatherForecast(weatherForecast)
                    }else{
                        db.weatherForecastDao().updateWeatherForecast(weatherForecast)
                    }
                }

            }
        }
    }


}