package com.spidergod.saveoassignment.repository

import com.spidergod.saveoassignment.data.remote.api.MovieApi
import com.spidergod.saveoassignment.data.remote.dto.movie_response_dto.MovieResponseDto
import com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto.MovieResponseForHorizontalPagerDto
import com.spidergod.saveoassignment.util.Resource
import javax.inject.Inject

class HomeScreenRepository @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getMovieForHorizontalPager(): Resource<MovieResponseForHorizontalPagerDto> {
        val response = try {
            movieApi.getMoviesForHorizontalPager()
        } catch (e: Exception) {
            return Resource.Error(e)
        }
        return Resource.Success(response)
    }

    suspend fun getMovieWithPage(page: Int): MovieResponseDto {
        return movieApi.getMoviesOfPage(page = page)
    }


}