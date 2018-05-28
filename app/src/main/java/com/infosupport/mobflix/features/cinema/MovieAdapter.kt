package com.infosupport.mobflix.features.cinema

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.infosupport.mobflix.BuildConfig
import com.infosupport.mobflix.R
import com.infosupport.mobflix.data.model.Movie
import com.infosupport.mobflix.util.extensions.inflate
import com.infosupport.mobflix.util.extensions.toLocalizedString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cinema_item_row.view.*
import java.text.DateFormat


class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflatedView = parent.inflate(R.layout.cinema_item_row, false)
        return MovieHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val itemMovie = movies[position]
        holder.bindMovie(itemMovie)
    }

    override fun getItemCount() = movies.size

    fun setItems(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        private var movie: Movie? = null

        fun bindMovie(movie: Movie) {
            this.movie = movie
            Picasso.with(view.context)
                    .load(BuildConfig.IMAGES_BASE_URL + movie.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .resize(171, 253)
                    .into(view.image)

            view.title.text = movie.title
            view.releaseDate.text = movie.releaseDate?.toLocalizedString(DateFormat.LONG)
            view.overview.text = movie.overview
            view.image.contentDescription = view.resources.getString(R.string.cinema_movie_poster_description, movie.title)
        }
    }
}
