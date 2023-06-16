package com.mycompany.weather.repository

import com.mycompany.weather.repository.service.IconsWeather
import com.mycompany.weather.repository.service.WeatherForecasts
import com.mycompany.weather.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Weather {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.URL.URL_BASE_WEATHER)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val getService: WeatherForecasts by lazy {
        retrofit.create(WeatherForecasts::class.java)
    }

}

object IconWeather{

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.URL.URL_BASE_ICON_WEATHER)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val getService: IconsWeather by lazy {
        retrofit.create(IconsWeather::class.java)
    }

}
