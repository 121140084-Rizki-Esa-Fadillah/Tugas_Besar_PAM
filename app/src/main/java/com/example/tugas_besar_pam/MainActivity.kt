package com.example.tugas_besar_pam

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.tugas_besar_pam.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Memeriksa apakah ada instance sebelumnya dari lokasi disimpan
        if (savedInstanceState != null) {
            currentLocation = savedInstanceState.getParcelable("location")
        } else {
            requestLocationPermission()
        }

        binding.navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> replaceFragment(SearchFragment().apply {
                    // Mengirim lokasi ke SearchFragment saat kembali ke sana
                    currentLocation?.let { location ->
                        val bundle = Bundle().apply {
                            putParcelable("location", location)
                        }
                        arguments = bundle
                    }
                })
                R.id.favorite -> replaceFragment(FavoriteFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Menyimpan lokasi saat instance di-save
        outState.putParcelable("location", currentLocation)
    }

    private fun requestLocationPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
            }
        }
        when {
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                currentLocation = location
                // Pass the location to the fragment
                val fragment = SearchFragment().apply {
                    val bundle = Bundle().apply {
                        putParcelable("location", location)
                    }
                    arguments = bundle
                }
                replaceFragment(fragment)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
