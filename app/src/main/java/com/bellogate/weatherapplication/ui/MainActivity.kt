package com.bellogate.weatherapplication.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bellogate.weatherapplication.R
import com.bellogate.weatherapplication.data.datasource.database.pojo.WeatherForecast
import com.bellogate.weatherapplication.data.datasource.network.pojo.WeatherForecastResponse
import com.bellogate.weatherapplication.ui.util.UIState
import com.bellogate.weatherapplication.ui.util.convertToCelsius
import com.bellogate.weatherapplication.ui.util.showAlert
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        setupUIState(UIState.SEARCHING_FOR_CURRENT_WEATHER_REPORT)

        //check or request permissions:
        if(viewModel checkPermissions this){
            fetchWeatherForecastByCoordinates()
        }else viewModel requestPermissions this //request user to grant permissions


        retryButton.setOnClickListener{
            fetchWeatherForecastByCoordinates()
        }

    }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Granted. Start getting the location information
                fetchWeatherForecastByCoordinates()

            }else{
                //prompt user to grant permission:
                this setupUIState UIState.PERMISSION_NOT_GRANTED
            }
        }
    }

    private infix fun setupUIState(state: UIState){

        when(state){
            UIState.SEARCHING_FOR_CURRENT_WEATHER_REPORT ->{
                progressBar.visibility = View.VISIBLE
                retryButton.visibility = View.INVISIBLE
                tvToday.visibility = View.INVISIBLE
                tvTemperature.visibility = View.INVISIBLE
                tvCondition.visibility = View.INVISIBLE
                tvDescription.visibility = View.INVISIBLE
                tvIndicator.visibility = View.INVISIBLE
            }

            UIState.FOUND_CURRENT_WEATHER_REPORT ->{
                progressBar.visibility = View.INVISIBLE
                tvToday.visibility = View.VISIBLE
                tvTemperature.visibility = View.VISIBLE
                tvCondition.visibility = View.VISIBLE
                tvDescription.visibility = View.VISIBLE
                tvIndicator.visibility = View.INVISIBLE
                retryButton.visibility = View.INVISIBLE
            }

            UIState.FAILED_TO_FIND_CURRENT_WEATHER_REPORT ->{
                progressBar.visibility = View.INVISIBLE
                retryButton.visibility = View.VISIBLE
                tvToday.visibility = View.INVISIBLE
                tvTemperature.visibility = View.INVISIBLE
                tvCondition.visibility = View.INVISIBLE
                tvDescription.visibility = View.INVISIBLE
                tvIndicator.visibility = View.INVISIBLE
            }


            UIState.PERMISSION_NOT_GRANTED ->{
                tvIndicator.visibility = View.VISIBLE
                tvIndicator.text = getString(R.string.permission_not_granted)
                tvIndicator.setBackgroundResource(R.color.black)
                progressBar.visibility = View.INVISIBLE
                tvIndicator.setOnClickListener{
                    if(!(viewModel checkPermissions this)){
                        viewModel requestPermissions this
                    }
                }
            }
        }
    }


    private fun fetchWeatherForecastByCoordinates(){
        this setupUIState UIState.SEARCHING_FOR_CURRENT_WEATHER_REPORT

        viewModel.getUserLocation(this, {
            lifecycleScope.launch {

                val weatherForecast: WeatherForecast? = withContext(Dispatchers.IO){
                    viewModel.fetchWeatherForecastByCoordinates(this@MainActivity, it)
                }

                if(weatherForecast != null){
                    displayWeatherForecast(weatherForecast)
                }else{
                    setupUIState(UIState.FAILED_TO_FIND_CURRENT_WEATHER_REPORT)
                    Toast.makeText(this@MainActivity, getString(R.string.offline), Toast.LENGTH_LONG).show()
                }
            }

        }, errorUserLocationIsTurnedOff = {
            showAlert(getString(R.string.turn_on_location))
        })

    }



    private fun displayWeatherForecast(weatherForecast: WeatherForecast) {
        setupUIState(UIState.FOUND_CURRENT_WEATHER_REPORT)

        weatherForecast.temp?.let {
            val celsius = convertToCelsius(it).toString()
            tvTemperature.text = "${celsius} ${getString(R.string.degree_symbol)}"
        }

        tvCondition.text = weatherForecast.main
        tvDescription.text = weatherForecast.description

    }

}
