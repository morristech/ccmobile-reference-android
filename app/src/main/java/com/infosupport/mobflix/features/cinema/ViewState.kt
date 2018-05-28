package com.infosupport.mobflix.features.cinema

import com.infosupport.mobflix.data.model.Movie

sealed class ViewState {
    object Loading: ViewState()
    object Error: ViewState()
    data class Data(val movies: List<Movie>) : ViewState()
}
