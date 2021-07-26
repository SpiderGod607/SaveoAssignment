package com.spidergod.saveoassignment.ui.presentation.movie_home_screen

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spidergod.saveoassignment.data.remote.dto.movie_response_dto.MovieResponseDtoItem
import com.spidergod.saveoassignment.repository.HomeScreenRepository

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MovieSource(
    private val repository: HomeScreenRepository
) : PagingSource<Int, MovieResponseDtoItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponseDtoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponseDtoItem> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        return try {
            val movieList = repository.getMovieWithPage(page = position)

            LoadResult.Page(
                data = movieList,
                prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}