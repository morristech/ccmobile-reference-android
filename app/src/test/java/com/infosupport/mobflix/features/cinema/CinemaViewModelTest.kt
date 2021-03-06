package com.infosupport.mobflix.features.cinema

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.infosupport.mobflix.data.MovieRepository
import com.infosupport.mobflix.data.model.Movie
import com.infosupport.mobflix.data.model.SearchResult
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import java.io.IOException


class CinemaViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private val repositoryMock: MovieRepository = mock()

    private val testScheduler = TestScheduler()

    private lateinit var systemUnderTest: CinemaViewModel

    @Test
    fun whenLoadMoviesThenMoviesAreRetrieved() {
        whenever(repositoryMock.getMoviesNowInCinema(anyInt()))
                .thenReturn(Flowable.just(moviesInCinema()))

        systemUnderTest = CinemaViewModel(repositoryMock, testScheduler, testScheduler)
        testScheduler.triggerActions()

        val viewState = systemUnderTest.viewState.value as ViewState.Data
        assertThat(viewState.movies).isEqualTo(moviesInCinema().results)
    }

    private fun moviesInCinema(): SearchResult {
        return SearchResult(
                page = 1,
                totalResults = 4,
                totalPages = 2,
                results = listOf(
                        Movie(id = 1),
                        Movie(id = 2)
                ))
    }

    @Test
    fun whenLoadMoreThenNextPageIsRequested() {
        whenever(repositoryMock.getMoviesNowInCinema(anyInt()))
                .thenReturn(Flowable.just(moviesInCinema()))

        systemUnderTest = CinemaViewModel(repositoryMock, testScheduler, testScheduler)
        testScheduler.triggerActions()
        systemUnderTest.onLoadMore()
        testScheduler.triggerActions()

        verify(repositoryMock).getMoviesNowInCinema(eq(2))
        val viewState = systemUnderTest.viewState.value as ViewState.Data
        assertThat(viewState.movies.size).isEqualTo(4)
    }

    @Test
    fun whenRefreshPulledThenFirstPageIsRetrieved() {
        whenever(repositoryMock.getMoviesNowInCinema(anyInt()))
                .thenReturn(Flowable.just(moviesInCinema()))
        systemUnderTest = CinemaViewModel(repositoryMock, testScheduler, testScheduler)
        testScheduler.triggerActions()

        systemUnderTest.onRefreshPulled()
        testScheduler.triggerActions()

        verify(repositoryMock, times(2)).getMoviesNowInCinema(eq(1))
        val viewState = systemUnderTest.viewState.value as ViewState.Data
        assertThat(viewState.movies.size).isEqualTo(2)
    }

    @Test
    fun whenErrorOccursThenViewStateIsError() {
        whenever(repositoryMock.getMoviesNowInCinema(anyInt()))
                .thenReturn(Flowable.error(IOException()))

        systemUnderTest = CinemaViewModel(repositoryMock, testScheduler, testScheduler)
        testScheduler.triggerActions()

        assertThat(systemUnderTest.viewState.value).isInstanceOf(ViewState.Error::class.java)
    }
}
