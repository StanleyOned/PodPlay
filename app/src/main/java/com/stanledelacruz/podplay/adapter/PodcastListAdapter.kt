package com.stanledelacruz.podplay.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stanledelacruz.podplay.R
import com.stanledelacruz.podplay.model.Podcast

class PodcastListAdapter(private var podcasts: List<Podcast>?,
                         private val podcastListAdapterListener: PodcastListAdapterListener,
                         private val parentActivity: Activity) : RecyclerView.Adapter<PodcastListAdapter.ViewHolder>() {

    interface PodcastListAdapterListener {
        fun onShowDetails(postcast: Podcast)
    }

    inner class ViewHolder(v: View, private val podcastListAdapterListener: PodcastListAdapterListener)
                            : RecyclerView.ViewHolder(v) {

        var podcast: Podcast? = null
        val nameTextView: TextView = v.findViewById(R.id.podcastNameTextView)
        val lastUpdatedTextView: TextView = v.findViewById(R.id.postcastLastUpdateTextView)
        val podcastImageView: ImageView = v.findViewById(R.id.podcastImage)

        init {
            v.setOnClickListener {
                podcast.let {
                    podcastListAdapterListener.onShowDetails(it!!)
                }
            }
        }
    }

    fun setSearchData(podcastData: List<Podcast>) {
        podcasts = podcastData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PodcastListAdapter.ViewHolder {
        return  ViewHolder(LayoutInflater.from(p0.context)
            .inflate(R.layout.search_item, p0, false),
            podcastListAdapterListener)
    }

    override fun getItemCount(): Int {
        return podcasts?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchViewList = podcasts ?: return
        val searchView = searchViewList[position]
        holder.podcast = searchView
        holder.nameTextView.text = searchView.name
        holder.lastUpdatedTextView.text = searchView.lastUpdated
        // Use Glide

        Glide.with(parentActivity)
            .load(searchView.imageUrl)
            .into(holder.podcastImageView)
    }
}