package com.mohamad.ghaleinterview.data.remote

import com.mohamad.ghaleinterview.data.remote.response.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeatherByLongAndLat(@Query("lat") lat:String,
    @Query("lon") long:String,@Query("appId") apiKey:String): Response<WeatherData>


    @GET("weather")
    suspend fun getCurrentWeatherByNameOfCity(@Query("q")cityName:String,
                                              @Query("appId") apiKey:String
                                              ):Response<WeatherData>


    @GET("")
    suspend fun getNextSevenDaysWeatherByLongAndLat()
}