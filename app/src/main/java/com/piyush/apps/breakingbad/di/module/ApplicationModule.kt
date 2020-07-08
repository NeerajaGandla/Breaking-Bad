package com.piyush.apps.breakingbad.di.module

import com.piyush.apps.breakingbad.BuildConfig
import com.piyush.apps.breakingbad.model.network.ApiHelper
import com.piyush.apps.breakingbad.model.network.ApiHelperImpl
import com.piyush.apps.breakingbad.model.network.BreakingBadApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */
@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun providesOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient.Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String, okHttpClient : OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideBreakingBadApi(retrofit: Retrofit) : BreakingBadApi = retrofit.create(BreakingBadApi::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper : ApiHelperImpl) : ApiHelper = apiHelper

}