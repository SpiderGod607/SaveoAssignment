package com.spidergod.saveoassignment.data.remote.api

import com.spidergod.saveoassignment.data.remote.dto.movie_response_dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/shows")
    suspend fun getMoviesOfPage(@Query("page") page: Int): MovieResponseDto


}