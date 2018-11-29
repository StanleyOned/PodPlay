package com.stanledelacruz.podplay.ui


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.stanledelacruz.podplay.R
import com.stanledelacruz.podplay.repository.ItunesRepo
import com.stanledelacruz.podplay.service.ItunesService
import com.stanledelacruz.podplay.service.PodcastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PodscastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podscast)
        setup()
    }

    fun setup() {
        val TAG = javaClass.simpleName
        val itunesService = ItunesService.instance
        val itunesRepo = ItunesRepo(itunesService)
        itunesRepo.searchByTerm(term = "Android Developer") {
            Log.i(TAG, "Results = $it")
        }
    }
}
