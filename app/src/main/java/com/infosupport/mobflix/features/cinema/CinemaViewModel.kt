package com.infosupport.mobflix.features.cinema

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.infosupport.mobflix.data.MovieRepository
import com.infosupport.mobflix.data.model.Movie
import com.infosupport.mobflix.data.model.SearchResult
import com.infosupport.mobflix.util.MoviePager
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class CinemaViewModel
@Inject constructor(private val movieRepository: MovieRepository,
                    @Named("processingScheduler") private val processingScheduler: Scheduler,
                    @Named("androidScheduler") private val androidScheduler: Scheduler) : ViewModel() {

    var viewState: MutableLiveData<ViewState> = MutableLiveData()

    private val disposables = CompositeDisposable()
    private var moviePager: MoviePager = MoviePager(null)
    private val movies: ArrayList<Movie> = ArrayList()

    init {
        loadMovies()
    }

    private fun loadMovies(page: Int = 1) {
        viewState.value = ViewState.Loading
        disposables.add(movieRepository.getMoviesNowInCinema(page)
                .subscribeOn(processingScheduler)
                .observeOn(androidScheduler)
                .subscribeBy(
                        onNext = { searchResult ->
                            addMoviesToList(searchResult)
                        },
                        onError = {
                            viewState.value = ViewState.Error
                        }
                )
        )
    }

    private fun addMoviesToList(searchResult: SearchResult) {
        moviePager = MoviePager(searchResult)
        movies.addAll(searchResult.results)
        viewState.value = ViewState.Data(movies)
    }

    fun onLoadMore() {
        if (moviePager.hasNextPage()) {
            loadMovies(moviePager.nextPage())
        }
    }

    fun onRefreshPulled() {
        movies.clear()
        loadMovies()
    }

    override fun onCleared() {
        disposables.dispose()
    }
}
