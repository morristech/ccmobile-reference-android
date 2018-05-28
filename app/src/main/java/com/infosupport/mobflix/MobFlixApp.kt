package com.infosupport.mobflix

import com.infosupport.mobflix.injection.DaggerAppComponent
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MobFlixApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        Picasso.setSingletonInstance(
                Picasso.Builder(this)
                        .indicatorsEnabled(BuildConfig.DEBUG)
                        .build())
    }
}
