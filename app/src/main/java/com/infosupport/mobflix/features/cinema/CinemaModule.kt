package com.infosupport.mobflix.features.cinema


import com.infosupport.mobflix.injection.scopes.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class CinemaModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun cinemaFragment(): CinemaFragment
}
