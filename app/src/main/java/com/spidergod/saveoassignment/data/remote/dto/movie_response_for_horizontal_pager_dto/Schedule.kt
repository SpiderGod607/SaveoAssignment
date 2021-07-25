package com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("time")
    val time: String?,
    @SerializedName("days")
    val days: List<String>?
)