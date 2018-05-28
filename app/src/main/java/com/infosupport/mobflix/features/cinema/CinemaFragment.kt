package com.infosupport.mobflix.features.cinema

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.infosupport.mobflix.R
import com.infosupport.mobflix.data.model.Movie
import com.infosupport.mobflix.extensions.asVisibility
import com.infosupport.mobflix.util.EndlessRecyclerViewScrollListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.cinema_fragment.*
import javax.inject.Inject


class CinemaFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: CinemaViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cinema_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CinemaViewModel::class.java)
        setupRecyclerView()
        setupEndlessScroll()
        setupPullToRefresh()
        observeStateChanges()
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = MovieAdapter(ArrayList())
        recyclerView.adapter = adapter
    }

    private fun setupEndlessScroll() {
        val endlessScrollListener: EndlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.onLoadMore()
            }

        }
        recyclerView.addOnScrollListener(endlessScrollListener)
    }

    private fun setupPullToRefresh() {
        pullToRefreshIndicator.setOnRefreshListener { viewModel.onRefreshPulled() }
    }

    private fun observeStateChanges() {
        viewModel.screenState.observe(this, Observer { screenState ->
            updateView(screenState)
        })
    }

    private fun updateView(screenState: ScreenState?) {
        when(screenState) {
            is ScreenState.Error -> showError()
            is ScreenState.Loading -> showLoading()
            is ScreenState.Data -> showData(screenState.movies)
        }
    }

    private fun showData(movies: List<Movie>) {
        adapter.setItems(movies)
        pullToRefreshIndicator.isRefreshing = false
        errorMessage.visibility = View.GONE
    }

    private fun showLoading() {
        pullToRefreshIndicator.isRefreshing = true
        errorMessage.visibility = View.GONE

    }

    private fun showError() {
        errorMessage.visibility = View.VISIBLE
        pullToRefreshIndicator.isRefreshing = false

    }
}
