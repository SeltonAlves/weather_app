package com.mycompany.weather.model.association

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Clouds(
    val all: Int
) : Parcelable