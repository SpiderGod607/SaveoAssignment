package com.spidergod.saveoassignment.data.converters

import com.spidergod.saveoassignment.data.parcelables.MovieDetailParcelable
import com.spidergod.saveoassignment.data.remote.dto.movie_response_dto.MovieResponseDtoItem
import com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto.MovieResponseForHorizontalPagerDtoItem

object Converter {

    fun movieResponseDtoItemToMovieDetailParcelable(
        movieResponseDtoItem: MovieResponseDtoItem
    ): MovieDetailParcelable {
        return movieResponseDtoItem.run {
            MovieDetailParcelable(
                name = name,
                runtime = runtime,
                premiered = premiered,
                genres = genres,
                rating = rating?.average,
                image = image?.original,
                summary = summary
            )
        }
    }


    fun movieResponseHorizontalPagerDtoItemToMovieDetailParcelable(
        movieResponseHorizontalPagerDtoItem: MovieResponseForHorizontalPagerDtoItem
    ): MovieDetailParcelable {
        return movieResponseHorizontalPagerDtoItem.run {
            MovieDetailParcelable(
                name = show?.name,
                runtime = show?.runtime,
                premiered = show?.premiered,
                genres = show?.genres,
                rating = show?.rating?.average,
                image = show?.image?.original,
                summary = show?.summary
            )
        }
    }


}