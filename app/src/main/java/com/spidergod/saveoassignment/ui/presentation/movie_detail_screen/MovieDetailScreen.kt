package com.spidergod.saveoassignment.ui.presentation.movie_detail_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.spidergod.saveoassignment.data.parcelables.MovieDetailParcelable
import com.spidergod.saveoassignment.ui.componenets.rating_bar.RatingBar
import com.spidergod.saveoassignment.ui.presentation.movie_home_screen.TopAppBar
import com.spidergod.saveoassignment.ui.theme.Blumine
import com.spidergod.saveoassignment.ui.theme.Downy

@ExperimentalAnimationApi
@Composable
fun MovieDetailScreen(
    movieDetailData: MovieDetailParcelable
) {


    Scaffold(
        topBar = {
            TopAppBar(
                iconLeft = Icons.Default.KeyboardArrowLeft,
                iconRight = Icons.Default.Share,
                title = "Movie"
            )
        }
    ) {
        var isTopDetailsVisible by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .background(color = Blumine)
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.size(5.dp))
            TopDetails(movieDetailData = movieDetailData)
            movieDetailData.summary?.let { it1 -> BottomDetail(it1, isTopDetailsVisible) }
        }
        if (!isTopDetailsVisible) {
            isTopDetailsVisible = true
        }
    }

}

@ExperimentalAnimationApi
@Composable
fun BottomDetail(data: String, isTopDetailsVisible: Boolean) {


    Box {
        Column {
            AnimatedVisibility(
                visible = isTopDetailsVisible,
                enter = slideInVertically(
                    // Start the slide from 40 (pixels) above where the content is supposed to go, to
                    // produce a parallax effect
                    initialOffsetY = { -50 }

                ) + expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 1000)
                ) + fadeIn(
                    initialAlpha = 0.3f,
                    animationSpec = tween(durationMillis = 1000)
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.TopEnd
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .background(Color.White)
                            .height(400.dp)
                            .scrollable(
                                state = rememberScrollState(),
                                orientation = Orientation.Vertical
                            )
                    ) {
                        Spacer(modifier = Modifier.height(56.dp))
                        Text(text = "Synopsis", modifier = Modifier.padding(horizontal = 15.dp))
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(text = data, modifier = Modifier.padding(horizontal = 15.dp))
                    }

                    Box(modifier = Modifier.padding(end = 25.dp)) {
                        Box(
                            modifier = Modifier.shadow(
                                elevation = 5.dp,
                                shape = RoundedCornerShape(25.dp)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(179.dp)
                                    .clip(RoundedCornerShape(25.dp))

                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "B O O K  N O W", color = Downy, fontSize = 18.sp)
                                }
                            }
                        }
                    }


                }

            }
        }

    }


}


@ExperimentalAnimationApi
@Composable
fun TopDetails(movieDetailData: MovieDetailParcelable) {
    var isTopDetailsVisible by remember {
        mutableStateOf(false)
    }

    Box() {
        Column {

            Row {
                Spacer(modifier = Modifier.size(25.dp))
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(190.dp)
                        .shadow(
                            elevation = 5.dp
                        ).background(Color.LightGray)
                ) {
                    Image(
                        painter = rememberImagePainter(data = movieDetailData.image),
                        contentDescription = movieDetailData.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.size(17.dp))
                AnimatedVisibility(
                    visible = isTopDetailsVisible,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth + fullWidth / 3 },

                        animationSpec = tween(durationMillis = 1000)
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = 1000)
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { 200 },
                        animationSpec = spring(stiffness = Spring.StiffnessHigh)
                    ) + fadeOut()
                ) {
                    Column {
                        Text(
                            text = movieDetailData.name ?: "No name",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(13.dp))
                        Text(
                            "R | ${movieDetailData.runtime} | ${movieDetailData.premiered}",
                            color = Color.White,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.size(19.dp))
                        horizontalList(movieDetailData.genres ?: arrayListOf("CRIME"))
                        Spacer(modifier = Modifier.size(52.dp))
                        //initial rating value is 3.2 here

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RatingBar(
                                rating = (movieDetailData.rating?.toFloat()?.div(10))?.times(5)
                                    ?: 0f,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(82.dp),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${
                                    (movieDetailData.rating?.toFloat()?.div(10))?.times(5) ?: 0f
                                }",
                                color = Color.White
                            )
                        }


                    }

                }

                Column(modifier = Modifier.width(15.dp)) {

                }

            }

        }

    }

    if (!isTopDetailsVisible) {
        isTopDetailsVisible = true
    }
}

@Composable
fun horizontalList(list: List<String>) {
    LazyRow {
        items(list) { currentItem ->
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .border(
                        width = 1.dp, color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = currentItem, fontSize = 11.sp, color = Color.White)
            }
        }
    }
}

