package com.vvchn.avitotesttask.data.remote.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toMovieInfo
import com.vvchn.avitotesttask.domain.models.MovieInfo
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class MoviesPagingSource (
    private val api: KinopoiskApi,
    private val query: String? = null,
    private val countries: Array<String>? = null,
    private val genres: Array<String>? = null,
    private val queryParameters: Map<String, String>? = null,
) : PagingSource<Int, MovieInfo>() {

    override fun getRefreshKey(state: PagingState<Int, MovieInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieInfo> {
        val page = params.key ?: 1
        val limit = params.loadSize

        if (query == null) {
            return try {
                val moviesResponse = api.getMovies(page = page, limit = limit, queryParameters = queryParameters, countries = countries, genres = genres)

                val nextKey = if (moviesResponse.total == 0 || moviesResponse.page == moviesResponse.pages) null else page + 1
                val prevKey = if (page == 1) null else page - 1

                LoadResult.Page(
                    data = moviesResponse.docs.map { movieInfoDto -> movieInfoDto.toMovieInfo() },
                    nextKey = nextKey,
                    prevKey = prevKey
                )
            } catch (e: Exception) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            }
            catch (e: IOException) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            }
            catch (e: SocketTimeoutException) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            }
        }
        else {
            return try {
                val moviesResponse = api.searchMovies(page = page, limit = limit, query = query)

                val nextKey = if (moviesResponse.total == 0 || moviesResponse.page == moviesResponse.pages) null else page + 1
                val prevKey = if (page == 1) null else page - 1

                LoadResult.Page(
                    data = moviesResponse.docs.map { movieInfoDto -> movieInfoDto.toMovieInfo() },
                    nextKey = nextKey,
                    prevKey = prevKey
                )
            } catch (e: Exception) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            }
            catch (e: IOException) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            }
            catch (e: SocketTimeoutException) {
                e.printStackTrace()
                LoadResult.Error(
                    throwable = e
                )
            }
        }
    }
}