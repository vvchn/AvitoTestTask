package com.vvchn.avitotesttask.data.remote.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toPosterInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class PostersPagingSource (
    private val api: KinopoiskApi,
    private val queryParameters: Map<String, String>?,
) : PagingSource<Int, PosterInfo>() {

    override fun getRefreshKey(state: PagingState<Int, PosterInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PosterInfo> {
        val page = params.key ?: 1
        val limit = params.loadSize

        return try {
            val moviesResponse = api.getPosters(page = page, limit = limit, queryParameters = queryParameters)

            val nextKey = if (moviesResponse.total == 0 || moviesResponse.page == moviesResponse.pages) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = moviesResponse.docs.map { posterInfoDto -> posterInfoDto.toPosterInfo() },
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