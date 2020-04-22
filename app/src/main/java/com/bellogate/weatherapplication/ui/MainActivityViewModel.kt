package com.bellogate.weatherapplication.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.bellogate.weatherapplication.data.Repository
import com.bellogate.weatherapplication.data.datasource.network.pojo.WeatherForecast
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
    suspend fun fetchWeatherForecastByCoordinates(location: Location): WeatherForecast?{

        return try {
            val response = Repository().fetchWeatherForecastByCoordinates(latitude = location.latitude,
                longitude = location.longitude)

            if(response.body() != null){
                response.body()
            }else{
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}