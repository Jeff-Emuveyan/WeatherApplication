package com.bellogate.weatherapplication.data.datasource.database.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weatherForecast")
class WeatherForecast(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                      @ColumnInfo(name = "temp") var temp: Double?,
                      @ColumnInfo(name = "main") var main: String?,
                      @ColumnInfo(name = "description") var description: String?)