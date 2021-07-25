package com.spidergod.saveoassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spidergod.saveoassignment.ui.presentation.movie_home_screen.MovieHomeScreen
import com.spidergod.saveoassignment.ui.theme.SaveoAssignmentTheme
import com.spidergod.saveoassignment.util.Constants.MOVIE_HOME_SCREEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

                }

            }
        }
    }
}

