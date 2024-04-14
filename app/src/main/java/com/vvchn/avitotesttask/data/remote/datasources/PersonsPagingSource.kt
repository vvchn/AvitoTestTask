package com.vvchn.avitotesttask.data.remote.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toPersonInfo
import com.vvchn.avitotesttask.domain.models.PersonInfo
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class PersonsPagingSource (
    private val api: KinopoiskApi,
    private val moviesId: String,
    private val selectedFields: Array<String>?,
    private val notNullFields: Array<String>?,
    private val professionValue:  Array<String>?,
) : PagingSource<Int, PersonInfo>() {

    override fun getRefreshKey(state: PagingState<Int, PersonInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonInfo> {
        val page = params.key ?: 1
        val limit = params.loadSize

        return try {
            val personsResponse = api.getPersons(page = page, limit = limit, moviesId, selectedFields, notNullFields, professionValue)

            val nextKey = if (personsResponse.total == 0 || personsResponse.page == personsResponse.pages) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = personsResponse.docs.map { personsInfoDto -> personsInfoDto.toPersonInfo() },
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
