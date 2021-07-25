package com.spidergod.saveoassignment.data.remote.dto.movie_response_dto


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    val average: Double?
)