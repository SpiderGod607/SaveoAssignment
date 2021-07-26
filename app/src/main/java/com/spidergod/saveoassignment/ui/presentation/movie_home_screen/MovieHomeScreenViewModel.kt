package com.spidergod.saveoassignment.ui.presentation.movie_home_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spidergod.saveoassignment.data.remote.dto.movie_response_dto.MovieResponseDtoItem
import com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto.MovieResponseForHorizontalPagerDtoItem
import com.spidergod.saveoassignment.repository.HomeScreenRepository

import com.spidergod.saveoassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieHomeScreenViewModel @Inject constructor(
    private val repository: HomeScreenRepository
) : ViewModel() {
    var isHorizontalPagerLoading: MutableState<Boolean>

    init {
        isHorizontalPagerLoading = mutableStateOf(false)
        getDataForHorizontalPager()
    }

    val horizontalPagerData = mutableStateListOf<MovieResponseForHorizontalPagerDtoItem>()

    fun getDataForHorizontalPager() {

        if ( !isHorizontalPagerLoading.value) {
            isHorizontalPagerLoading.value = true
            viewModelScope.launch {
                val response = repository.getMovieForHorizontalPager()

                if (horizontalPagerData.isNotEmpty()) {
                    horizontalPagerData.clear()
                }

                if (response is Resource.Success) {
                    response.data?.let { horizontalPagerData.addAll(it) }
                }
                isHorizontalPagerLoading.value = false
            }
        }
    }

    val moviesMainListPaging: Flow<PagingData<MovieResponseDtoItem>> =
        Pager(PagingConfig(pageSize = 100)) {
            MovieSource(repository)
        }.flow


}