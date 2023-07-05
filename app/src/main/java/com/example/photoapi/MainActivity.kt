package com.example.photoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.photoapi.Adapter.WallpaperAdapter
import com.example.photoapi.Modal.PhotosModal
import com.example.photoapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var auth = "YKRwNZEdFIQAI598LYkoohDgcvCi1oM6deNueyL0ttXOuWSsIppUg57y"
    lateinit var adapter: WallpaperAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = WallpaperAdapter()

        binding.btnChangeImage.setOnClickListener {
            callApi()
        }
    }

    private fun callApi() {
        var txt = binding.edtHint.text.toString()

        var api: ApiInterface = ApiClient.getApiClient()!!.create(ApiInterface::class.java)
        api.getPhoto(auth,txt).enqueue(object : Callback<PhotosModal> {
            override fun onResponse(call: Call<PhotosModal>, response: Response<PhotosModal>) {
                if (response.isSuccessful) {
                    var photos = response.body()?.photos
                    adapter.setPhotos(photos)
                    binding.rcvPhotos.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    binding.rcvPhotos.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PhotosModal>, t: Throwable) {

            }

        })
    }


}