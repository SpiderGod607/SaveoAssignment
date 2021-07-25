package com.spidergod.saveoassignment.data.remote.dto.movie_response_dto


import com.google.gson.annotations.SerializedName

data class CountryX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("timezone")
    val timezone: String?
)