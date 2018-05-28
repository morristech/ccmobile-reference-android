package com.infosupport.mobflix.data

import com.infosupport.mobflix.data.model.SearchResult
import io.reactivex.Flowable

class MovieRepository(private val movieDbApi: TheMovieDbApi) {

    fun getMoviesNowInCinema(page: Int = 1): Flowable<SearchResult> {
        return movieDbApi.getMoviesInCinema(page)
    }
}
