package com.vvchn.avitotesttask.data.remote.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toStudioInfo
import com.vvchn.avitotesttask.domain.models.StudioInfo
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class StudiosPagingSource (
    private val api: KinopoiskApi,
    private val queryParameters: Map<String, List<String>>?,
) : PagingSource<Int, StudioInfo>() {

    override fun getRefreshKey(state: PagingState<Int, StudioInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StudioInfo> {
        val page = params.key ?: 1
        val limit = params.loadSize

        return try {
            val moviesResponse = api.getMovieProductionCompanies(page = page, limit = limit, queryParameters = queryParameters)

            LoadResult.Page(
                data = moviesResponse.docs.map { studioInfoDto -> studioInfoDto.toStudioInfo() },
                nextKey = if (moviesResponse.page == moviesResponse.pages) null else page + 1,
                prevKey = null
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