package com.infosupport.mobflix.data.model

import com.google.gson.annotations.SerializedName

data class SearchResult(var page: Int = 0,
                        @SerializedName("total_results") var totalResults: Int = 0,
                        @SerializedName("total_pages") var totalPages: Int = 0,
                        var results: List<Movie> = emptyList())
