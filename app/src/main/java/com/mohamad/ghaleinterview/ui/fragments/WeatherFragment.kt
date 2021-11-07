package com.mohamad.ghaleinterview.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohamad.ghaleinterview.R
import com.mohamad.ghaleinterview.adapters.DailyWeatherAdapter
import com.mohamad.ghaleinterview.data.remote.response.dailyWeather.Daily
import com.mohamad.ghaleinterview.databinding.FragmentWeatherBinding
import com.mohamad.ghaleinterview.other.Constance.ICON_BASE_URL
import com.mohamad.ghaleinterview.other.Status
import com.mohamad.ghaleinterview.ui.viewModels.DailyDetail
import com.mohamad.ghaleinterview.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WeatherFragment:Fragment(R.layout.fragment_weather) {

    private  var _binding:FragmentWeatherBinding? = null

    private lateinit var mainViewModel:MainViewModel


    private val binding get() = _binding!!

    @Inject
    lateinit var dailyWeatherAdapter: DailyWeatherAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWeatherBinding.bind(view)
        initMainViewModel()
        setUpRecyclerView()
        subscribeToObservers()
        setOnQueryTextListenerAndGetCurrentWeatherByCityName()
        dailyWeatherAdapter.setOnClickListener {
            navigateToDailyWeatherDetailFragment(it)
        }




    }

    private fun initMainViewModel(){
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun subscribeToObservers(){
        mainViewModel.weatherData.observe(viewLifecycleOwner,  { result ->
            when(result.status){
                Status.LOADING -> {
                showProgressBar()
                }

                Status.SUCCESS -> {
                    hideProgressBar()
                    result.data?.let {
                        binding.tvCityName.text = it.name
                        val temp = it.main.temp.toInt().toString() + "\u2109"
                        binding.tvDegree.text = temp
                        setWeatherIcon(it.weather[0].icon)



                    }
                }

                Status.ERROR -> {
                 hideProgressBar()
                }


            }

        })
        mainViewModel.dailyWeatherData.observe(viewLifecycleOwner,{

          dailyWeatherAdapter.submitList(it.daily)


        })
    }



 private fun setWeatherIcon(iconPath:String){
     Glide.with(requireContext()).load("$ICON_BASE_URL$iconPath.png").into(binding.ivWeatherIcon)
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



    private fun setUpRecyclerView(){
        binding.rvDailyWeather.adapter = dailyWeatherAdapter
        binding.rvDailyWeather.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)

    }


    private fun navigateToDailyWeatherDetailFragment(daily:Daily){
         mainViewModel.setDailyWeather(DailyDetail(daily.temp.day,
         daily.temp.night,
         daily.wind_speed,daily.humidity,
         daily.weather[0].icon
             ))
        findNavController().navigate(R.id.action_weatherFragment_to_dailyWeatherDetailFragment)
    }


    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }







}