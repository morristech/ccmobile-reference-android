package com.infosupport.mobflix.data

import com.infosupport.mobflix.BuildConfig
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    internal fun providesMovieRepository(): MovieRepository {
        return MovieRepository(createMovieDbApi())
    }

    @Provides
    @Named("processingScheduler")
    internal fun provideProcessingScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named("androidScheduler")
    internal fun provideAndroidScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    private fun createMovieDbApi(): TheMovieDbApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(TheMovieDbApi::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AddApiKeyInterceptor())
                .addInterceptor(createLoggingInterceptor())
                .build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }

        return loggingInterceptor
    }
}
