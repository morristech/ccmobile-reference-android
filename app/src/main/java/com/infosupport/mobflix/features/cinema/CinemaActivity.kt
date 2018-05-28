package com.infosupport.mobflix.features.cinema

import android.os.Bundle
import com.infosupport.mobflix.R
import com.infosupport.mobflix.data.AddApiKeyInterceptor
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class CinemaActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragmentProvidedByDagger: CinemaFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cinema_activity)
        title = getString(R.string.cinema_list_title)

        var listFragment: CinemaFragment? = supportFragmentManager.findFragmentById(R.id.cinemaListFragment) as CinemaFragment?

        if (listFragment == null) {
            listFragment = fragmentProvidedByDagger
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.cinemaListFragment, listFragment)
                    .commit()
        }
    }
}
