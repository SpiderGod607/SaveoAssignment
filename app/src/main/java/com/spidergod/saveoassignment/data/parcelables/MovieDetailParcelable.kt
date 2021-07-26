package com.spidergod.saveoassignment.data.parcelables

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailParcelable(
    val name: String?,
    val runtime: Int?,
    val premiered: String?,
    val genres: List<String>?,
    val rating: Double?,
    val image: String?,
    val summary: String?,
) : Parcelable