package com.infosupport.mobflix.features.cinema

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.infosupport.mobflix.data.MovieRepository
import com.infosupport.mobflix.data.model.Movie
import com.infosupport.mobflix.data.model.SearchResult
import com.infosupport.mobflix.util.MoviePager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CinemaViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var moviePager: MoviePager = MoviePager(null)

    private val movies: ArrayList<Movie> = ArrayList()

    var screenState: LiveData<ScreenState> = MutableLiveData()

    init {
        loadMovies()
    }

    private fun loadMovies(page: Int = 1) {
        (screenState as MutableLiveData).value = ScreenState.Loading
        disposables.add(movieRepository.getMoviesNowInCinema(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { searchResult ->
                            addMoviesToList(searchResult)
                        },
                        onError = {
                            (screenState as MutableLiveData).value = ScreenState.Error
                        }
                )
        )
    }

    private fun addMoviesToList(searchResult: SearchResult) {
        moviePager = MoviePager(searchResult)
        movies.addAll(searchResult.results)
        (screenState as MutableLiveData).value = ScreenState.Data(movies)
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
