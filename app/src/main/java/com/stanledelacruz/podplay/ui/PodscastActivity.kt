package com.stanledelacruz.podplay.ui


import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.support.v7.widget.SearchView
import android.view.View
import com.stanledelacruz.podplay.R
import com.stanledelacruz.podplay.adapter.PodcastListAdapter
import com.stanledelacruz.podplay.model.Podcast
import com.stanledelacruz.podplay.repository.ItunesRepo
import com.stanledelacruz.podplay.service.ItunesService
import com.stanledelacruz.podplay.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_podscast.*

class PodscastActivity : AppCompatActivity(), PodcastListAdapter.PodcastListAdapterListener {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var podcastListAdapter: PodcastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podscast)
        setupToolbar()
        setupViewModels()
        updateControls()
        handleIntent(intent)
    }

    override fun onShowDetails(postcast: Podcast) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //1
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        //2
        val searchMenuItem = menu?.findItem(R.id.search_item)
        val searchView = searchMenuItem?.actionView as SearchView
        //3
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        //4
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return  true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun performSearch(term: String) {
        showProgressBar()
        searchViewModel.searchPodcasts(term) { results ->
            hideProgressBar()
            toolbar.title = getString(R.string.search_results)
            podcastListAdapter.setSearchData(results)
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            query.let {
                performSearch(it!!)
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupViewModels() {
        val service = ItunesService.instance
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchViewModel.iTunesRepo = ItunesRepo(service)
    }

    private fun updateControls() {
        podcastRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        podcastRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration =
            android.support.v7.widget.DividerItemDecoration(podcastRecyclerView.context,
                layoutManager.orientation)

        podcastRecyclerView.addItemDecoration(dividerItemDecoration)

        podcastListAdapter =
                PodcastListAdapter(null, this, this)
        podcastRecyclerView.adapter = podcastListAdapter
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}
