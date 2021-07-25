package com.spidergod.saveoassignment.data.remote.dto.movie_response_dto


import com.google.gson.annotations.SerializedName

data class MovieResponseDtoItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("averageRuntime")
    val averageRuntime: Int?,
    @SerializedName("premiered")
    val premiered: String?,
    @SerializedName("officialSite")
    val officialSite: String?,
    @SerializedName("schedule")
    val schedule: Schedule?,
    @SerializedName("rating")
    val rating: Rating?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("network")
    val network: Network?,
    @SerializedName("webChannel")
    val webChannel: WebChannel?,
    @SerializedName("dvdCountry")
    val dvdCountry: Any?,
    @SerializedName("externals")
    val externals: Externals?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("updated")
    val updated: Int?,
    @SerializedName("_links")
    val links: Links?
)