package com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: Self?,
    @SerializedName("previousepisode")
    val previousepisode: Previousepisode?
)