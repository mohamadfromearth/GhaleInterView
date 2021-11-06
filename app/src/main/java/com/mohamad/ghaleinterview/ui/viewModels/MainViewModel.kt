package com.mohamad.ghaleinterview.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamad.ghaleinterview.GhaleApplication
import com.mohamad.ghaleinterview.data.remote.response.WeatherData
import com.mohamad.ghaleinterview.other.Resource
import com.mohamad.ghaleinterview.other.Status
import com.mohamad.ghaleinterview.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val weatherRepository: WeatherRepository
):ViewModel(){

   private val _weatherData = MutableLiveData<Resource<WeatherData>>()
   val weatherData = _weatherData




    fun getWeatherDataByLongAndLat(lat:String,lon:String) = viewModelScope.launch {
        _weatherData.postValue(Resource(Status.LOADING,null,null))
        try {
            val response = weatherRepository.getCurrentWeatherByLongAndLat(lat,lon)
        _weatherData.postValue(handleResponse(response))

        }catch (t:Throwable){
        _weatherData.postValue(Resource.error("Network failure",null))
        }
    }


    fun getWeatherDataByCityName(cityName:String) = viewModelScope.launch {
        _weatherData.postValue(Resource(Status.LOADING,null,null))
        try {
            val response = weatherRepository.getCurrentWeatherByCityName(cityName)
            _weatherData.postValue(handleResponse(response))
        }catch (t:Throwable){
            _weatherData.postValue(Resource.error("Network failure",null))
        }
    }





    private fun<T> handleResponse(response:Response<T>):Resource<T>{
        if (response.isSuccessful){
            response.body()?.let {  resultResponse ->
                return Resource.success(resultResponse)

            }
        }
        return Resource.error(response.message(),null)
    }



    }

