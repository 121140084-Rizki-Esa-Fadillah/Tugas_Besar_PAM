package com.example.tugas_besar_pam

import UlasanAdapter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tugas_besar_pam.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: ImageAdapter
    private lateinit var ulasanAdapter: UlasanAdapter
    private val listGambar = ArrayList<ImageData>()
    private val listUlasan = ArrayList<String>() // List untuk menampung teks ulasan
    private lateinit var dots: ArrayList<TextView>
    private val slideHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ImageAdapter(listGambar)
        binding.vpGambar.adapter = adapter
        dots = ArrayList()

        // Inisialisasi adapter untuk RecyclerView ulasan
        ulasanAdapter = UlasanAdapter(listUlasan)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerUlasan.layoutManager = layoutManager
        binding.recyclerUlasan.adapter = ulasanAdapter

        val fsqId = intent.getStringExtra("FSQ_ID") ?: ""
        fetchPhotos(fsqId)
        fetchPlaceDetails(fsqId)
        fetchTips(fsqId) // Mengambil ulasan dari API Foursquare

        // Mendengarkan klik tombol kembali ke halaman pencarian
        binding.bgBacktoSearch.setOnClickListener {
            onBackPressed()
        }

        binding.vpGambar.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectDot(position)
                super.onPageSelected(position)

                slideHandler.removeCallbacks(sliderRun)
                slideHandler.postDelayed(sliderRun, 3000)
            }
        })
    }

    private val sliderRun = Runnable {
        binding.vpGambar.currentItem = (binding.vpGambar.currentItem + 1) % listGambar.size
    }

    private fun selectDot(position: Int) {
        for (i in 0 until listGambar.size) {
            if (i == position) {
                dots[i].setTextColor(ContextCompat.getColor(this, R.color.primary))
            } else {
                dots[i].setTextColor(ContextCompat.getColor(this, R.color.sub_primary))
            }
        }
    }

    private fun setIndicator() {
        dots.clear()
        binding.dotsIndicator.removeAllViews()

        for (i in 0 until listGambar.size) {
            dots.add(TextView(this))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }

            dots[i].textSize = 18f
            binding.dotsIndicator.addView(dots[i])
        }
        selectDot(0)
    }

    private fun fetchPlaceDetails(fsqId: String) {
        val apiService = RetrofitInstance.api
        apiService.getPlaceDetails(fsqId, "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=").enqueue(object : Callback<PlaceDetails> {
            override fun onResponse(call: Call<PlaceDetails>, response: Response<PlaceDetails>) {
                val placeDetails = response.body()
                if (placeDetails != null) {
                    // Tampilkan data pada antarmuka pengguna (UI)
                    binding.NamaTempat.text = placeDetails.name
                    binding.AlamatTempat.text = placeDetails.location.formatted_address
                    binding.RatingTempat.text = "Rating: ${placeDetails.rating?.toString() ?: "N/A"}"
                    binding.JamOperasional.text = "Jam Operasional: ${placeDetails.hours?.display ?: "N/A"}"
                }
            }

            override fun onFailure(call: Call<PlaceDetails>, t: Throwable) {
                // Tangani kesalahan saat mengambil data
            }
        })
    }

    private fun fetchPhotos(fsqId: String) {
        val apiService = RetrofitInstance.api
        apiService.getPhotos(fsqId, "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=").enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                val photos = response.body()
                if (!photos.isNullOrEmpty()) {
                    for (photo in photos) {
                        val photoUrl = "${photo.prefix}${photo.width}x${photo.height}${photo.suffix}"
                        listGambar.add(ImageData(photoUrl))
                    }
                    adapter.notifyDataSetChanged()
                    setIndicator()
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun fetchTips(fsqId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val apiService = RetrofitInstance.api
            val response = apiService.getTips(fsqId, "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=")
            if (response.isSuccessful) {
                val tips = response.body()
                tips?.let {
                    for (tip in it) {
                        listUlasan.add(tip.text)
                    }
                    ulasanAdapter.notifyDataSetChanged()
                }
            } else {
                // Handle error
            }
        }
    }
}
