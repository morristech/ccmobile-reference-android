package com.infosupport.mobflix.features.cinema

import com.infosupport.mobflix.data.model.Movie

sealed class ScreenState {
    object Loading: ScreenState()
    object Error: ScreenState()
    data class Data(val movies: List<Movie>) : ScreenState()
}
