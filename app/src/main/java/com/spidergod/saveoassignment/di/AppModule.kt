package com.spidergod.saveoassignment.di

import com.spidergod.saveoassignment.data.remote.api.MovieApi
import com.spidergod.saveoassignment.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            .build()
    }

    @Provides
    @Singleton
    fun providesMoviesApi(
        retrofit: Retrofit
    ): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

}