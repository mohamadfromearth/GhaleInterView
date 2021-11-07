package com.mohamad.ghaleinterview.data.remote

import com.mohamad.ghaleinterview.data.remote.response.WeatherData
import com.mohamad.ghaleinterview.data.remote.response.dailyWeather.DailyWeatherData
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


    @GET("onecall")
    suspend fun getDailyWeatherData(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("exclude") exclude:String,
        @Query("appId") apiKey: String
        
    ):Response<DailyWeatherData>
}