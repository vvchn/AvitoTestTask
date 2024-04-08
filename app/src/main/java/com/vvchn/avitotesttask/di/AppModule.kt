package com.vvchn.avitotesttask.di

import com.vvchn.avitotesttask.BuildConfig
import com.vvchn.avitotesttask.common.ApiKeyInterceptor
import com.vvchn.avitotesttask.common.Constants
import com.vvchn.avitotesttask.data.api.KinopoiskApi
import com.vvchn.avitotesttask.data.repository.KinopoiskRepository
import com.vvchn.avitotesttask.data.repository.KinopoiskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKinopoiskApi(): KinopoiskApi {

        val modifierOkhttpClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY)).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(modifierOkhttpClient)
            .build()
            .create(KinopoiskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKinopoiskRepository(api: KinopoiskApi): KinopoiskRepository {
        return KinopoiskRepositoryImpl(api)
    }
}