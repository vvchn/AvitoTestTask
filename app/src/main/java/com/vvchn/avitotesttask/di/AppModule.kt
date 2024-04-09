package com.vvchn.avitotesttask.di

import com.vvchn.avitotesttask.BuildConfig
import com.vvchn.avitotesttask.common.ApiKeyInterceptor
import com.vvchn.avitotesttask.data.api.KinopoiskApi
import com.vvchn.avitotesttask.data.repository.KinopoiskRepositoryImpl
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKinopoiskApi(): KinopoiskApi {

        val modifierOkhttpClient: OkHttpClient =
            OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY))
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(modifierOkhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKinopoiskRepository(api: KinopoiskApi): KinopoiskRepository {
        return KinopoiskRepositoryImpl(api)
    }
}