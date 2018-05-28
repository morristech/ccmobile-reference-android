package com.infosupport.mobflix.injection.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.infosupport.mobflix.features.cinema.CinemaViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelBindingModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CinemaViewModel::class)
    internal abstract fun cinemaViewModel(viewModel: CinemaViewModel): ViewModel

    // Add more ViewModels here
}
