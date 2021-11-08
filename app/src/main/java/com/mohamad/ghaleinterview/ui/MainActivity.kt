package com.mohamad.ghaleinterview.ui

import com.google.android.gms.tasks.OnTokenCanceledListener



import android.Manifest
import android.annotation.SuppressLint



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.mohamad.ghaleinterview.R
import com.mohamad.ghaleinterview.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val mainViewModel:MainViewModel by viewModels()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationPermissionRequestAndGetWeatherByLatAndLon()
    }
    @SuppressLint("MissingPermission")
    private fun locationPermissionRequestAndGetWeatherByLatAndLon(){
        val locationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            val isFineLocationGranted = it.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false)
        if (isFineLocationGranted){
            fusedLocationClient.getCurrentLocation(104,
                    object : CancellationToken() {
                        override fun isCancellationRequested(): Boolean {
                           return false
                        }

                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            return this
                        }


                    }).addOnSuccessListener { loc ->
                   mainViewModel.shouldUpdateWeatherData.observe(this, { shouldUpdate ->
                       if (shouldUpdate){
                           mainViewModel.getWeatherDataByLongAndLat(loc.latitude,loc.longitude)
                       }
                   })
                    mainViewModel.setShouldUpdateWeatherData(false)





                }

        }else{
            Toast.makeText(this,"Some functionality of the app does not work cause you denied permission",Toast.LENGTH_SHORT).show()
        }
        }

        locationPermission.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }













}