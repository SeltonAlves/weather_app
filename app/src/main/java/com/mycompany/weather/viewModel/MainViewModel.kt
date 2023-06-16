package com.mycompany.weather.viewModel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mycompany.weather.model.WeatherForecastsModel
import com.mycompany.weather.repository.weather.IconWeatherRepository
import com.mycompany.weather.repository.weather.WeatherRepository
import com.mycompany.weather.util.ApiResponse
import java.net.HttpURLConnection.HTTP_OK

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryWeather = WeatherRepository()
    private val repositoryIconWeather = IconWeatherRepository()

    private var _weather = MutableLiveData<WeatherForecastsModel>()
    val weather: LiveData<WeatherForecastsModel> = _weather

    private var _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    private var _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    fun getWeather(latitude: String, longitude: String) {
        repositoryWeather.getWeather(
            longitude = longitude,
            latitude = latitude,
            listener = object : ApiResponse<WeatherForecastsModel> {
                override fun onSuccess(result: WeatherForecastsModel) {
                    if (result.cod == HTTP_OK) {
                        _weather.value = result
                        getIconsWeather(result.weather[0].icon)
                    }
                }

                override fun onFailure(message: String) {
                    _response.value = message
                }

            })
    }

    private fun getIconsWeather(code: String) {
        repositoryIconWeather.getIcon(code, object : ApiResponse<Bitmap> {
            override fun onSuccess(result: Bitmap) {
                _image.value = result
            }

            override fun onFailure(message: String) {
                _response.value = message
            }

        })
    }

}
