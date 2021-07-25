package com.spidergod.saveoassignment.data.remote.dto.movie_response_dto


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("original")
    val original: String?
)