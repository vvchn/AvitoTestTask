package com.vvchn.avitotesttask.di

import com.vvchn.avitotesttask.BuildConfig
import com.vvchn.avitotesttask.common.ApiKeyInterceptor
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.repository.KinopoiskRepositoryImpl
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import com.vvchn.avitotesttask.domain.usecases.GetAllPossibleCountriesUseCase
import com.vvchn.avitotesttask.domain.usecases.GetAllPossibleGenresUseCase
import com.vvchn.avitotesttask.domain.usecases.GetMoviePersonsUseCase
import com.vvchn.avitotesttask.domain.usecases.GetMovieProductionCompaniesUseCase
import com.vvchn.avitotesttask.domain.usecases.GetMoviesUseCase
import com.vvchn.avitotesttask.domain.usecases.GetPostersUseCase
import com.vvchn.avitotesttask.domain.usecases.GetReviewsByMovieIDUseCase
import com.vvchn.avitotesttask.domain.usecases.GetReviewsCountUseCase
import com.vvchn.avitotesttask.domain.usecases.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
                .addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY)).build()

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

    @Provides
    @Singleton
    fun provideGetAllPossibleCountriesUseCase(repository: KinopoiskRepository): GetAllPossibleCountriesUseCase {
        return GetAllPossibleCountriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPossibleGenresUseCase(repository: KinopoiskRepository): GetAllPossibleGenresUseCase {
        return GetAllPossibleGenresUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMovieProductionCompaniesUseCase(repository: KinopoiskRepository): GetMovieProductionCompaniesUseCase {
        return GetMovieProductionCompaniesUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: KinopoiskRepository): GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPostersUseCase(repository: KinopoiskRepository): GetPostersUseCase {
        return GetPostersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetReviewsByMovieIDUseCase(repository: KinopoiskRepository): GetReviewsByMovieIDUseCase {
        return GetReviewsByMovieIDUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetReviewsCountUseCase(repository: KinopoiskRepository): GetReviewsCountUseCase {
        return GetReviewsCountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(repository: KinopoiskRepository): SearchMoviesUseCase {
        return SearchMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMoviePersonsUseCase(repository: KinopoiskRepository): GetMoviePersonsUseCase {
        return GetMoviePersonsUseCase(repository)
    }
}