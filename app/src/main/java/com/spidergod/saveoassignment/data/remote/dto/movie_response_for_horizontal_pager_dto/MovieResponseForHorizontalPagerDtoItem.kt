package com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto


import com.google.gson.annotations.SerializedName

data class MovieResponseForHorizontalPagerDtoItem(
    @SerializedName("score")
    val score: Double?,
    @SerializedName("show")
    val show: Show?
)