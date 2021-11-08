package com.mohamad.ghaleinterview.ui.viewModels

import androidx.lifecycle.*
import com.mohamad.ghaleinterview.data.remote.response.WeatherData
import com.mohamad.ghaleinterview.data.remote.response.dailyWeather.DailyWeatherData
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
   val weatherData: LiveData<Resource<WeatherData>> = _weatherData


   private val _dailyWeatherData = MutableLiveData<DailyWeatherData>()
   val dailyWeatherData: LiveData<DailyWeatherData> = _dailyWeatherData



    private val _shouldUpdateWeatherData = MutableLiveData<Boolean>()
    val shouldUpdateWeatherData:LiveData<Boolean> = _shouldUpdateWeatherData


   private val _dailyDetail = MutableLiveData<DailyDetail>()
   val dailyDetail:LiveData<DailyDetail> = _dailyDetail

   fun setDailyWeather(dailyDetail: DailyDetail){
       _dailyDetail.postValue(dailyDetail)
   }

   fun setShouldUpdateWeatherData(shouldUpdate:Boolean){
       _shouldUpdateWeatherData.postValue(shouldUpdate)
   }


  init {
      _shouldUpdateWeatherData.value = true

  }



    fun getWeatherDataByLongAndLat(lat:Double?,lon:Double?) = viewModelScope.launch {
        _weatherData.postValue(Resource(Status.LOADING,null,null))
        if (lat == null || lon == null){
            _weatherData.postValue(Resource.error("Location not found",null))
            return@launch
        }
        try {
            val response = weatherRepository.getCurrentWeatherByLongAndLat(lat.toString(),lon.toString())
             getDailyWeatherData(lat.toString(),lon.toString())

        _weatherData.postValue(handleResponse(response))

        }catch (t:Throwable){
        _weatherData.postValue(Resource.error("Network failure",null))
        }
    }


    fun getWeatherDataByCityName(cityName:String) = viewModelScope.launch {
        _weatherData.postValue(Resource(Status.LOADING,null,null))
        try {
            val response = weatherRepository.getCurrentWeatherByCityName(cityName)
            val lat = response.body()?.coord?.lat.toString()
            val lon = response.body()?.coord?.lon.toString()
            getDailyWeatherData(lat,lon)
            _weatherData.postValue(handleResponse(response))
        }catch (t:Throwable){
            _weatherData.postValue(Resource.error("Network failure",null))
        }
    }



    private suspend fun getDailyWeatherData(lat:String,lon:String){
        val response = weatherRepository.getDailyWeatherData(lat,lon)
        if (response.isSuccessful){
            _dailyWeatherData.postValue(response.body())
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


data class DailyDetail(
    val tempDay:Double,
    val tempNight:Double,
    val windSpeed:Double,
    val humidity:Int,
    val weatherIcon:String
)

