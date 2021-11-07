package com.mohamad.ghaleinterview.data.remote.response.dailyWeather

data class DailyWeatherData(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)