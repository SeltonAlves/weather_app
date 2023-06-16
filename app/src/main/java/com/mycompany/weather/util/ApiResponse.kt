package com.mycompany.weather.util

interface ApiResponse <T> {
    fun onSuccess(result: T)
    fun onFailure(message:String)
}