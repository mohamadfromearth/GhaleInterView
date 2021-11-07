package com.mohamad.ghaleinterview.repository

import com.mohamad.ghaleinterview.data.remote.WeatherApi
import com.mohamad.ghaleinterview.other.Constance.API_KEY
import com.mohamad.ghaleinterview.other.Constance.EXCLUDE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {


    suspend fun getCurrentWeatherByLongAndLat(lat:String,lon:String) =
        weatherApi.getCurrentWeatherByLongAndLat(lat,lon,API_KEY)


    suspend fun getCurrentWeatherByCityName(cityName:String) =
        weatherApi.getCurrentWeatherByNameOfCity(cityName, API_KEY)



    suspend fun getDailyWeatherData(lat:String,lon:String) =
        weatherApi.getDailyWeatherData(lat,lon, EXCLUDE,API_KEY)




}