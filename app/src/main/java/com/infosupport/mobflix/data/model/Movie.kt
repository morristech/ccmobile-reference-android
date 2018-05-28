package com.infosupport.mobflix.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie(var id: Int = 0,
                 var title: String? = null,
                 var overview: String? = null,
                 @SerializedName("vote_count") var voteCount: Int = 0,
                 @SerializedName("poster_path") var posterPath: String? = null,
                 @SerializedName("genre_ids") var genreIds: List<Int> = emptyList(),
                 @SerializedName("release_date") var releaseDate: Date? = null)
