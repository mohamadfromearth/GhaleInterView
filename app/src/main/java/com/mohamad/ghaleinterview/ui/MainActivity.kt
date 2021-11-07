package com.mohamad.ghaleinterview.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mohamad.ghaleinterview.R
import com.mohamad.ghaleinterview.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val mainViewModel:MainViewModel by viewModels()

    private  var location:Location? = null

   private var lon:Double? = null

   private var lat:Double? = null

   private lateinit var locationListener: LocationListener




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        locationsPermissionRequest()









        }



    private fun getLocationPermission(){

    }




    @SuppressLint("MissingPermission")
    private fun locationsPermissionRequest(){
        val locationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){

        val isFineLocationGranted = it.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false)
        if (isFineLocationGranted){


                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->


               loc?.let { location ->
                   mainViewModel.getWeatherDataByLongAndLat(location.latitude.toString(),location.longitude.toString())

               }



                }







        }
        }

        locationPermission.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }




    @SuppressLint("MissingPermission")
    private fun locationPermissionRequest():ActivityResultLauncher<Array<out String?>?>{

        val locationPermissionRequest = registerForActivityResult(

            ActivityResultContracts.RequestMultiplePermissions()
        ){ permissions ->
            when{
              //  permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false) ->{

              //  }
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false) ->{
                    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        val location:Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                       val lon = location?.longitude.toString()
                        val lat = location?.latitude.toString()
                        Toast.makeText(this,"$lon $lat",Toast.LENGTH_SHORT).show()
                    }




                }

                else ->{

                }
            }
        }

        return locationPermissionRequest


    }


    private fun launchLocationPermissionRequest(){
        locationPermissionRequest().launch(arrayOf(
           Manifest.permission.ACCESS_FINE_LOCATION,
           Manifest.permission.ACCESS_COARSE_LOCATION))
    }





}