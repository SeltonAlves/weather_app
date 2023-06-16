package com.mycompany.weather.util

class Constants {
    object URL {
        const val URL_BASE_WEATHER: String = "https://api.openweathermap.org/data/2.5/"
        const val GET_WEATHER_FORECASTS: String = "weather?"
        const val units : String = "metric"
        const val KEY : String = "d938810e07c2c7d2c60d209e2d1d6142"
        const val LANG : String = "pt_br"
        const val URL_BASE_ICON_WEATHER : String = "https://openweathermap.org/"
        const val GET_ICON_WEATHER : String = "img/wn/{code}@2x.png"
    }
}