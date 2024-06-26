package com.vvchn.avitotesttask.data.remote.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toReviewInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ReviewsPagingSource (
    private val api: KinopoiskApi,
    private val queryParameters: Map<String, String>?,
) : PagingSource<Int, ReviewInfo>() {

    override fun getRefreshKey(state: PagingState<Int, ReviewInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewInfo> {
        val page = params.key ?: 1
        val limit = params.loadSize

        return try {
            val reviewResponse = api.getReviewsByMovieID(page = page, limit = limit, queryParameters = queryParameters)

            val nextKey = if (reviewResponse.total == 0 || reviewResponse.page == reviewResponse.pages) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = reviewResponse.docs.map { reviewInfoDto -> reviewInfoDto.toReviewInfo() },
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