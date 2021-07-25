package com.spidergod.saveoassignment.ui.presentation.states

sealed class HorizontalPagerStates(data: String) {
    class Success
    class Loading
    class Error(data: String)
}
