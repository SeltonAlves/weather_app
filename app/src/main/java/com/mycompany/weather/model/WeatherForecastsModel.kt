package com.mycompany.weather.model

import android.os.Parcelable
import com.mycompany.weather.model.association.Clouds
import com.mycompany.weather.model.association.Coord
import com.mycompany.weather.model.association.Main
import com.mycompany.weather.model.association.Sys
import com.mycompany.weather.model.association.Weather
import com.mycompany.weather.model.association.Wind
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherForecastsModel(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Long,
    val id: Long,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) : Parcelable