package com.example.task7.di

import com.example.task7.api.ClevertecApi
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    private companion object {
        const val retrofitBaseUrl = "http://test.clevertec.ru/tt/"
    }

    @Singleton
    @Provides
    fun provideRetrofitApi(): ClevertecApi = Retrofit.Builder()
        .baseUrl(retrofitBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ClevertecApi::class.java)

}