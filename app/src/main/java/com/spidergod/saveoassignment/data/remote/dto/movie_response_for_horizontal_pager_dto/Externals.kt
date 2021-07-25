package com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto


import com.google.gson.annotations.SerializedName

data class Externals(
    @SerializedName("tvrage")
    val tvrage: Any?,
    @SerializedName("thetvdb")
    val thetvdb: Int?,
    @SerializedName("imdb")
    val imdb: String?
)