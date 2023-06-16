package com.mycompany.weather.repository.service

import com.mycompany.weather.model.WeatherForecastsModel
import com.mycompany.weather.util.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherForecasts {

    @GET(Constants.URL.GET_WEATHER_FORECASTS)
    fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") unit: String = Constants.URL.units,
        @Query("appid") apiKey: String = Constants.URL.KEY,
        @Query("lang") language: String = Constants.URL.LANG
    ): Call<WeatherForecastsModel>

}