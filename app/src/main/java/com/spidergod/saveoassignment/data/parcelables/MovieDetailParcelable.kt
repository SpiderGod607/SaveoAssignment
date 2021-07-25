package com.spidergod.saveoassignment.data.parcelables

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailParcelable(
    val name: String,
    val runtime: Int,
    val premiered: Int,
    val genres: List<String>,
    val rating: Float,
    val image: String,
    val summary: String,
) : Parcelable