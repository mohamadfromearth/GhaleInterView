package com.mohamad.ghaleinterview.di

import android.content.Context
import android.location.LocationManager
import androidx.core.content.ContextCompat.getSystemService
import com.mohamad.ghaleinterview.data.remote.WeatherApi
import com.mohamad.ghaleinterview.other.Constance.BASE_URL
import com.mohamad.ghaleinterview.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideWeatherApi():WeatherApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi):WeatherRepository {
        return WeatherRepository(weatherApi)
    }

    @Singleton
    @Provides
    fun provideLocationManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager



}