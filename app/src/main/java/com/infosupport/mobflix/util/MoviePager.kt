package com.infosupport.mobflix.util

import com.infosupport.mobflix.data.model.SearchResult

class MoviePager(private val searchResult: SearchResult?) {

    fun hasNextPage(): Boolean {
        if (searchResult != null) {
            return searchResult.page < searchResult.totalPages
        }
        return false
    }

    fun nextPage(): Int {
        if (searchResult != null) {
            return searchResult.page + 1
        }
        return 1
    }
}
