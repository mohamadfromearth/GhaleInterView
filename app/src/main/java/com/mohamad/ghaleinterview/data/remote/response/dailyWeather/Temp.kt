package com.mohamad.ghaleinterview.data.remote.response.dailyWeather

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)