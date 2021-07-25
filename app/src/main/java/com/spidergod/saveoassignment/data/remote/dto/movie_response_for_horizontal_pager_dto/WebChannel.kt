package com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto


import com.google.gson.annotations.SerializedName

data class WebChannel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("country")
    val country: Country?
)