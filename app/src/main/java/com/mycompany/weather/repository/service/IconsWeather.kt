package com.mycompany.weather.repository.service

import com.mycompany.weather.util.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IconsWeather {

    @GET(Constants.URL.GET_ICON_WEATHER)
    fun getIconWeather(@Path("code") code: String): Call<ResponseBody>
}