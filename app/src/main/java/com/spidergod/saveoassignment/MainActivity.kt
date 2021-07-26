package com.spidergod.saveoassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spidergod.saveoassignment.data.parcelables.MovieDetailParcelable
import com.spidergod.saveoassignment.ui.presentation.movie_detail_screen.MovieDetailScreen
import com.spidergod.saveoassignment.ui.presentation.movie_home_screen.MovieHomeScreen
import com.spidergod.saveoassignment.ui.theme.SaveoAssignmentTheme
import com.spidergod.saveoassignment.util.Constants.DETAIL_SCREEN_ARGUMENT
import com.spidergod.saveoassignment.util.Constants.MOVIE_DETAIL_SCREEN
import com.spidergod.saveoassignment.util.Constants.MOVIE_HOME_SCREEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            SaveoAssignmentTheme {

                NavHost(navController = navController, startDestination = MOVIE_HOME_SCREEN) {

                    composable(MOVIE_HOME_SCREEN) {
                        MovieHomeScreen(
                            navController = navController
                        )
                    }
                    composable(MOVIE_DETAIL_SCREEN) {
                        val movieDetailParcelable =
                            navController.previousBackStackEntry?.arguments?.getParcelable<MovieDetailParcelable>(
                                DETAIL_SCREEN_ARGUMENT
                            )

                        if (movieDetailParcelable != null) {
                            MovieDetailScreen(
                                movieDetailData = movieDetailParcelable
                            )
                        }

                    }

                }

            }
        }
    }
}

