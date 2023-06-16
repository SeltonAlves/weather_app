package com.mycompany.weather.repository.weather

import com.google.gson.Gson
import com.mycompany.weather.model.ErroResponse
import com.mycompany.weather.model.WeatherForecastsModel
import com.mycompany.weather.repository.Weather
import com.mycompany.weather.util.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class WeatherRepository {

    private val remote = Weather.getService

    fun getWeather(
        longitude: String,
        latitude: String,
        listener: ApiResponse<WeatherForecastsModel>
    ) {
        val call = remote.getWeather(latitude = latitude, longitude = longitude)
        call.enqueue(object : Callback<WeatherForecastsModel> {
            override fun onResponse(
                call: Call<WeatherForecastsModel>,
                response: Response<WeatherForecastsModel>
            ) {
                if (response.code() == HTTP_OK) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.toString(), ErroResponse::class.java)
                    listener.onFailure(error.message)
                }
            }

            override fun onFailure(call: Call<WeatherForecastsModel>, t: Throwable) {
                listener.onFailure("Erro, verifique sua conexão ou seu gps se estão ligados, se já estiverem ligados tentaremos resolver em breve.")
            }

        })

    }

}