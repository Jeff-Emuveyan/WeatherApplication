package com.bellogate.weatherapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.bellogate.weatherapplication.data.datasource.database.AppDatabase
import com.bellogate.weatherapplication.data.datasource.database.dao.WeatherForecastDao
import com.bellogate.weatherapplication.data.datasource.database.pojo.WeatherForecast
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This class tests the Room database of the app through the Repository.kt class.
 * NOTE: An ANDROID DEVICE must be connected to the IDE in other to run this tests.
 * **/
@RunWith(AndroidJUnit4::class)
class RepositoryInstrumentedTest {

    private lateinit var weatherForecastDao: WeatherForecastDao
    private lateinit var db: AppDatabase

    /**
     * Create the database
     * **/
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        weatherForecastDao = db.weatherForecastDao()
    }

    /**
     * Close the database
     * */
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    /**
     * This test checks to see if we can successfully save and retrieve weather forecast from the
     * database.
     * **/
    @Test
    @Throws(Exception::class)
    fun saveAndRetrieveWeatherForecast() = runBlocking{

        val weatherForecast = WeatherForecast(cityName = "lagos",
            lat = 33.33,
            lon = 44.44)

        weatherForecastDao.saveWeatherForecast(weatherForecast)

        val retrievedWeatherForecast =
            weatherForecastDao.getWeatherForecastSynchronously("lagos")

        assert(weatherForecast.cityName == retrievedWeatherForecast?.cityName)
    }
}