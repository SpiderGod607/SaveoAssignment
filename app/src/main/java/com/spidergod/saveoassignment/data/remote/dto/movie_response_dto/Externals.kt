package com.spidergod.saveoassignment.data.remote.dto.movie_response_dto


import com.google.gson.annotations.SerializedName

data class Externals(
    @SerializedName("tvrage")
    val tvrage: Int?,
    @SerializedName("thetvdb")
    val thetvdb: Int?,
    @SerializedName("imdb")
    val imdb: String?
)