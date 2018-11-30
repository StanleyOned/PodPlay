package com.stanledelacruz.podplay.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.stanledelacruz.podplay.model.Podcast
import com.stanledelacruz.podplay.repository.ItunesRepo
import com.stanledelacruz.podplay.service.PodcastResponse
import com.stanledelacruz.podplay.util.DateUtils

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    var iTunesRepo: ItunesRepo? = null

    private fun initializedPodcast(itunesPodcast: PodcastResponse.ItunesPodcast) : Podcast {
        return  Podcast(itunesPodcast.collectionCensoredName,
                        DateUtils.jsonDateToShortDate(itunesPodcast.releaseDate),
                        itunesPodcast.artworkUrl30,
                        itunesPodcast.feedUrl)
    }

    fun searchPodcasts(term: String, callback: (List<Podcast>) -> Unit) {
        iTunesRepo?.searchByTerm(term) { results ->
            if (results == null) {
                callback(emptyList())
            } else {
                val searchViews = results.map { podcast ->
                    initializedPodcast(podcast)
                }
                searchViews.let { it -> callback(it) }
            }
        }
    }
}