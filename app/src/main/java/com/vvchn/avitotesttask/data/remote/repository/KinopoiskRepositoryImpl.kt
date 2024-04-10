package com.vvchn.avitotesttask.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toMovieInfo
import com.vvchn.avitotesttask.data.remote.common.toPoster
import com.vvchn.avitotesttask.data.remote.common.toStudio
import com.vvchn.avitotesttask.data.remote.datasources.MoviesPagingSource
import com.vvchn.avitotesttask.data.remote.datasources.PostersPagingSource
import com.vvchn.avitotesttask.data.remote.datasources.ReviewsPagingSource
import com.vvchn.avitotesttask.data.remote.datasources.StudiosPagingSource
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.models.StudioInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class KinopoiskRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : KinopoiskRepository {
    override suspend fun getMovieDetail(id: Int): MovieInfo = api.getMovieDetail(id).toMovieInfo()

    override fun getMovies(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<MovieInfo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            MoviesPagingSource(api, query = null, queryParameters)
        }
    ).flow

    override fun searchMovies(
        page: Int,
        limit: Int,
        query: String
    ): Flow<PagingData<MovieInfo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            MoviesPagingSource(api, query)
        }
    ).flow

    override fun getReviewsByMovieID(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<ReviewInfo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            ReviewsPagingSource(api, queryParameters)
        }
    ).flow

    override fun getMovieProductionCompanies(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<StudioInfo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            StudiosPagingSource(api, queryParameters)
        }
    ).flow

    override fun getPosters(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<PosterInfo>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            PostersPagingSource(api, queryParameters)
        }
    ).flow
}