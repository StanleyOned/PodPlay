package com.stanledelacruz.podplay.repository

import com.stanledelacruz.podplay.service.ItunesService
import com.stanledelacruz.podplay.service.PodcastResponse
import com.stanledelacruz.podplay.service.PodcastResponse.ItunesPodcast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItunesRepo(private val itunesService: ItunesService) {

    //2
    fun searchByTerm(term: String, callBack: (List<ItunesPodcast>?) -> Unit) {
        //3
        val podcastCall = itunesService.searchPodcastbyTerm(term)

        podcastCall.enqueue(object : Callback<PodcastResponse> {

            override fun onFailure(call: Call<PodcastResponse>, t: Throwable) {
                callBack(null)
            }

            override fun onResponse(call: Call<PodcastResponse>, response: Response<PodcastResponse>) {
               val body = response?.body()
                callBack(body?.results)
            }
        })
    }
}