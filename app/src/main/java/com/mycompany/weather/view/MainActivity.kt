package com.mycompany.weather.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mycompany.weather.R
import com.mycompany.weather.databinding.ActivityMainBinding
import com.mycompany.weather.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationPermissionCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        observe()
        handleClick()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocations()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun handleClick() {
        binding.imageSearch.setOnClickListener {
              checkLocationPermission()
        }
    }

    private fun checkLocationPermission() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val enableGpsDialog = AlertDialog.Builder(this)
                .setTitle(R.string.gps_disabled)
                .setMessage(R.string.want_to_activate)
                .setPositiveButton(R.string.yes) { _, _ ->
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

         return enableGpsDialog.show()
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocations()
        } else {
            val permissionDialog = AlertDialog.Builder(this)
                .setTitle(R.string.location_permission)
                .setMessage(R.string.permission)
                .setPositiveButton(R.string.yes) { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        locationPermissionCode
                    )
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(
                        this,
                        R.string.we_need_your_permission,
                        Toast.LENGTH_LONG
                    ).show()
                }
                .create()

            permissionDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observe() {
        viewModel.weather.observe(this) { weather ->
            if (weather != null) {
                binding.textHumidity.text = weather.main.humidity.toString() + "%"
                binding.textTemperatures.text = weather.main.temp.toString().take(2) + "ºc"
                binding.textConditions.text = weather.weather[0].description
                binding.textSpeed.text = weather.wind.speed.toString() + "km/h"
                binding.textLocation.text = weather.name
            } else {
                viewModel.response.observe(this) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.image.observe(this){ it ->
            if (it != null){
               binding.imageIconWeather.setImageBitmap(it)
           }else{
               viewModel.response.observe(this){
                   Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
               }
           }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocations() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                viewModel.getWeather(location.latitude.toString(), location.longitude.toString())
            }
        }
    }
}
