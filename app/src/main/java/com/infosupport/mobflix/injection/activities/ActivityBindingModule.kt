package com.infosupport.mobflix.injection.activities

import com.infosupport.mobflix.features.cinema.CinemaActivity
import com.infosupport.mobflix.features.cinema.CinemaModule
import com.infosupport.mobflix.injection.scopes.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [CinemaModule::class])
    abstract fun cinemaActivity(): CinemaActivity

    // Add new activities here
}
