package com.mohamad.ghaleinterview.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.GnssAntennaInfo
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mohamad.ghaleinterview.R
import com.mohamad.ghaleinterview.databinding.FragmentWeatherBinding
import com.mohamad.ghaleinterview.other.Status
import com.mohamad.ghaleinterview.ui.viewModels.MainViewModel
import java.security.Permission



class WeatherFragment:Fragment(R.layout.fragment_weather) {

    private lateinit var binding:FragmentWeatherBinding

    private lateinit var mainViewModel:MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherBinding.bind(view)
        initMainViewModel()
        subscribeToObservers()
        setOnQueryTextListenerAndGetCurrentWeatherByCityName()




    }

    private fun initMainViewModel(){
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun subscribeToObservers(){
        mainViewModel.weatherData.observe(viewLifecycleOwner,  { result ->
            when(result.status){
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    result.data?.let {
                        binding.tvCityName.text = it.name
                        binding.tvDegree.text = it.main.temp.toInt().toString() + "\u2109"
                        setWeatherIcon(it.weather[0].icon)



                    }
                }

                Status.ERROR -> {

                }

            }

        })
    }



 private fun setWeatherIcon(iconPath:String){
     Glide.with(requireContext()).load("http://openweathermap.org/img/w/$iconPath"+".png").into(binding.ivWeatherIcon)
 }



    private fun setOnQueryTextListenerAndGetCurrentWeatherByCityName(){
        binding.weatherSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(cityName: String?): Boolean {
                cityName?.let {
                    mainViewModel.getWeatherDataByCityName(it)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }











}