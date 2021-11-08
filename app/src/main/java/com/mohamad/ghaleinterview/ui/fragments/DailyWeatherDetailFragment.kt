package com.mohamad.ghaleinterview.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mohamad.ghaleinterview.R
import com.mohamad.ghaleinterview.databinding.FragmentDailyWeatherDetailBinding
import com.mohamad.ghaleinterview.other.Constance.ICON_BASE_URL
import com.mohamad.ghaleinterview.ui.viewModels.DailyDetail
import com.mohamad.ghaleinterview.ui.viewModels.MainViewModel

class DailyWeatherDetailFragment:Fragment(R.layout.fragment_daily_weather_detail) {

    private  var _binding:FragmentDailyWeatherDetailBinding? = null

    private val binding get() = _binding!!

    private lateinit var mainViewModel:MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDailyWeatherDetailBinding.bind(view)
        initViewModel()
        subscribeToObservers()

    }



    private fun subscribeToObservers(){
        mainViewModel.dailyDetail.observe(viewLifecycleOwner,{
            setDataToViews(it)
        })
    }


    private fun setDataToViews(dailyDetail:DailyDetail){
        val iconUri = "$ICON_BASE_URL${dailyDetail.weatherIcon}.png"
        Glide.with(requireContext()).load(iconUri).into(binding.ivIcon)
        val dayTemp = dailyDetail.tempDay.toInt().toString()
        val nightTemp = dailyDetail.tempNight.toInt().toString()
        binding.tvDayTemp.text = "Day temp: $dayTemp℉"
        binding.tvNightTemp.text = "Night temp: $nightTemp℉"
        binding.windSpeed.text = "Wind speed: " + dailyDetail.windSpeed.toString() + "m/s"

    }


    private fun initViewModel(){
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}