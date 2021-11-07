package com.mohamad.ghaleinterview.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mohamad.ghaleinterview.R
import com.mohamad.ghaleinterview.databinding.FragmentDailyWeatherDetailBinding
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

    }



    private fun subscribeToObservers(){
        mainViewModel.dailyDetail.observe(viewLifecycleOwner,{
            setDataToViews(it)
        })
    }


    private fun setDataToViews(dailyDetail:DailyDetail){
        Glide.with(requireContext()).load(dailyDetail.weatherIcon).into(binding.ivIcon)
        val dayTemp = dailyDetail.tempDay.toInt().toString()
        val nightTemp = dailyDetail.tempNight.toInt().toString()
        binding.tvDayTemp.text = "$dayTemp℉"
        binding.tvNightTemp.text = "$nightTemp℉"
        binding.windSpeed.text = dailyDetail.windSpeed.toString()

    }


    private fun initViewModel(){
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}