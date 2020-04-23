package com.bellogate.weatherapplication.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bellogate.weatherapplication.data.Repository
import com.bellogate.weatherapplication.data.datasource.database.pojo.WeatherForecast
import com.bellogate.weatherapplication.data.datasource.network.pojo.WeatherForecastResponse
import com.bellogate.weatherapplication.ui.util.USER_CURRENT_CITY
import com.google.android.gms.location.*


const val PERMISSION_ID = 4040

class MainActivityViewModel : ViewModel() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    infix fun checkPermissions(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    }


    infix fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID)
    }


    /**This will check if the user has turned on location from the setting,
     * Cause user may grant the app to user location but if the location setting is off then it'll be of no use.**/
    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    /**
     * Returns the current location of the user
     */
    fun getUserLocation(context: Context,
                        userLocation: (Location) -> Unit,
                        errorUserLocationIsTurnedOff: (Boolean) -> Unit): Location?{

        if(isLocationEnabled(context)){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {task ->
                val location = task.result
                if(location != null){
                    userLocation.invoke(location)
                    Log.e(MainActivityViewModel::class.java.simpleName,
                        "lat: ${location.latitude}, " +
                                "lon: ${location.longitude}")
                }else{
                    //if for any reason we are unable to get the location, we try an alternate method:
                    val mLocationRequest = LocationRequest()
                    mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    mLocationRequest.interval = 0
                    mLocationRequest.fastestInterval = 0
                    mLocationRequest.numUpdates = 1
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
                    fusedLocationProviderClient.requestLocationUpdates(
                        mLocationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                val mLastLocation = locationResult.lastLocation

                                if(mLastLocation != null){
                                    userLocation.invoke(mLastLocation)
                                    Log.e(MainActivityViewModel::class.java.simpleName,
                                        "lat: ${mLastLocation.latitude}, " +
                                                "lon: ${mLastLocation.longitude}")
                                }
                            }
                        },
                        Looper.myLooper()
                    )
                }

            }

        }else{
            errorUserLocationIsTurnedOff.invoke(true)
        }
        return null
    }



    /**
     * Makes network call to fetch weather forecast using location
     ***/
    suspend fun fetchWeatherForecastByCoordinates(context: Context, location: Location): WeatherForecast?{

        try {
            val response = Repository(context).fetchWeatherForecastByCoordinates(latitude = location.latitude,
                longitude = location.longitude)

            if(response.body() != null){
                val webResourceResponse = response.body() as WeatherForecastResponse
                val weatherForecast = WeatherForecast(
                    lat = webResourceResponse.coord?.lat,
                    lon = webResourceResponse.coord?.lon,
                    temp = webResourceResponse.main?.temp,
                    main = webResourceResponse.weather?.get(0)?.main,
                    description = webResourceResponse.weather?.get(0)?.description)

                Repository(context).saveWeatherForecast(viewModelScope, weatherForecast)

                return weatherForecast
            }

        } catch (e: Exception) {
            //at this point, the user is offline, so we fetch the weather forecast from the DB:
            return fetchWeatherForecastByNameFromDB(context, USER_CURRENT_CITY)
        }
        return null
    }



    /**
     * Searches DB from weather forecast using 'name'
     */
    suspend fun fetchWeatherForecastByNameFromDB(context: Context, cityName: String): WeatherForecast? =
        Repository(context).fetchWeatherForecastByNameFromDB(cityName)


}