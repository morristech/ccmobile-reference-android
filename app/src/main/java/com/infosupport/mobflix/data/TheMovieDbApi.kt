package com.infosupport.mobflix.data

import com.infosupport.mobflix.data.model.SearchResult
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("discover/movie?primary_release_date.gte=2018-05-01&primary_release_date.lte=2018-05-30")
    fun getMoviesInCinema(@Query("page") page: Int): Flowable<SearchResult>
}
