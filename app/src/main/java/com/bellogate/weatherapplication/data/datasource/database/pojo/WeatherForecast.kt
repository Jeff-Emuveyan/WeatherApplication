package com.bellogate.weatherapplication.data.datasource.database.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bellogate.weatherapplication.ui.util.USER_CURRENT_CITY


@Entity(tableName = "weatherForecast")
data class WeatherForecast(@PrimaryKey var cityName: String = USER_CURRENT_CITY,
                           @ColumnInfo(name = "lat") var lat: Double? = 0.0,
                           @ColumnInfo(name = "lon") var lon: Double? = 0.0,
                           @ColumnInfo(name = "temp") var temp: Double?,
                           @ColumnInfo(name = "main") var main: String?,
                           @ColumnInfo(name = "description") var description: String?){


    constructor(): this(temp = 0.0, main = "", description = "")
}

