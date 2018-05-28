package com.infosupport.mobflix.injection

import android.app.Application
import com.infosupport.mobflix.MobFlixApp
import com.infosupport.mobflix.data.DataModule
import com.infosupport.mobflix.injection.activities.ActivityBindingModule
import com.infosupport.mobflix.injection.viewmodels.ViewModelBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DataModule::class,
    ViewModelBindingModule::class,
    ActivityBindingModule::class])
interface AppComponent : AndroidInjector<MobFlixApp> {

    override fun inject(instance: MobFlixApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

}
