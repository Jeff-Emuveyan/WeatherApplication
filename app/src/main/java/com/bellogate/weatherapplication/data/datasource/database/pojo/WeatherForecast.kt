package com.bellogate.weatherapplication.data.datasource.database.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weatherForecast")
data class WeatherForecast(@PrimaryKey var cityName: String = "userCurrentCity",
                           @ColumnInfo(name = "lat") var lat: Double? = 0.0,
                           @ColumnInfo(name = "lon") var lon: Double? = 0.0,
                           @ColumnInfo(name = "temp") var temp: Double?,
                           @ColumnInfo(name = "main") var main: String?,
                           @ColumnInfo(name = "description") var description: String?){


    constructor(): this(temp = 0.0, main = "", description = "")
}

