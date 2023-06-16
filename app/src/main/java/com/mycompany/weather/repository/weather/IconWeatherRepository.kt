package com.mycompany.weather.repository.weather

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mycompany.weather.repository.IconWeather
import com.mycompany.weather.util.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class IconWeatherRepository {
    private val remote = IconWeather.getService

    fun getIcon(code: String, listener: ApiResponse<Bitmap>) {
        val call = remote.getIconWeather(code)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    val inputStream: InputStream? = response.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    if (bitmap != null) {
                        listener.onSuccess(bitmap)
                    } else {
                        listener.onFailure("Erro ao converter a imagem")
                    }
                } else {
                    listener.onFailure("Erro no carregamento")
                }


            }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    listener.onFailure("Erro")
                }

            })
        }
    }