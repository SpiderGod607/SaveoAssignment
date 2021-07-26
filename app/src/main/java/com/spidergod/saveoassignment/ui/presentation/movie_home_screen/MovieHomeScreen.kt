package com.spidergod.saveoassignment.ui.presentation.movie_home_screen

import android.os.Bundle
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.spidergod.saveoassignment.data.converters.Converter.movieResponseDtoItemToMovieDetailParcelable
import com.spidergod.saveoassignment.data.converters.Converter.movieResponseHorizontalPagerDtoItemToMovieDetailParcelable
import com.spidergod.saveoassignment.data.parcelables.MovieDetailParcelable
import com.spidergod.saveoassignment.data.remote.dto.movie_response_dto.MovieResponseDtoItem
import com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto.MovieResponseForHorizontalPagerDtoItem
import com.spidergod.saveoassignment.ui.componenets.movie_pager.Pager
import com.spidergod.saveoassignment.ui.componenets.movie_pager.PagerState
import com.spidergod.saveoassignment.ui.theme.Blumine
import com.spidergod.saveoassignment.util.Constants.DETAIL_SCREEN_ARGUMENT
import com.spidergod.saveoassignment.util.Constants.MOVIE_DETAIL_SCREEN
import kotlin.math.abs
import kotlin.math.min

@Composable
fun MovieHomeScreen(
    navController: NavController,
    viewModel: MovieHomeScreenViewModel = hiltViewModel()
) {

    val lazyListScrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(title = if (lazyListScrollState.firstVisibleItemIndex > 1) "Now Showing" else "Movies")
        }
    ) {
        Box(
            modifier = Modifier
                .background(color = Blumine)
                .fillMaxSize()
                .padding(it)
        ) {

            val movieListItems = viewModel.moviesMainListPaging.collectAsLazyPagingItems()

            LazyColumn(
                state = lazyListScrollState
            ) {
                item{
                    Spacer(modifier = Modifier.size(10.dp))
                }
                item {
                    HorizontalPager(viewModel.horizontalPagerData,
                        onItemClick = { responseHorizontalPagerItem ->
                            NavigateToMovieDetailScreen(
                                navController = navController,
                                movieDetailParcelable = movieResponseHorizontalPagerDtoItemToMovieDetailParcelable(
                                    responseHorizontalPagerItem
                                )
                            )
                        },
                        isHorizontalPagerLoading = viewModel.isHorizontalPagerLoading.value,
                        retry = {
                            viewModel.getDataForHorizontalPager()
                        }
                    )
                }
                item {
                    Column {
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Now Showing", color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.size(25.dp))
                    }

                }

                items(movieListItems.itemCount) { currentItemIndex ->
                    if (currentItemIndex == 0 || currentItemIndex % 3 == 0) {
                        Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                movieListItems[currentItemIndex]?.let { it1 ->
                                    MovieManListGridItem(
                                        movieData = it1,
                                        onItemClick = { movieResponseItem ->
                                            NavigateToMovieDetailScreen(
                                                navController = navController,
                                                movieDetailParcelable = movieResponseDtoItemToMovieDetailParcelable(
                                                    movieResponseItem
                                                )
                                            )
                                        }
                                    )
                                }
                                if (currentItemIndex < movieListItems.itemCount - 1) {
                                    movieListItems[currentItemIndex + 1]?.let { it1 ->
                                        MovieManListGridItem(
                                            movieData = it1,
                                            onItemClick = { movieResponseItem ->
                                                NavigateToMovieDetailScreen(
                                                    navController = navController,
                                                    movieDetailParcelable = movieResponseDtoItemToMovieDetailParcelable(
                                                        movieResponseItem
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                                if (currentItemIndex < movieListItems.itemCount - 2) {
                                    movieListItems[currentItemIndex + 2]?.let { it1 ->
                                        MovieManListGridItem(
                                            movieData = it1,
                                            onItemClick = { movieResponseItem ->
                                                NavigateToMovieDetailScreen(
                                                    navController = navController,
                                                    movieDetailParcelable = movieResponseDtoItemToMovieDetailParcelable(
                                                        movieResponseItem
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                movieListItems.apply {

                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = movieListItems.loadState.refresh as LoadState.Error
                            item {
                                Box(contentAlignment = Alignment.Center) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Error :" + e.error.localizedMessage,
                                            color = Color.White
                                        )
                                        Button(onClick = {
                                            movieListItems.retry()
                                        }) {
                                            Text(text = "Retry", color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}

fun NavigateToMovieDetailScreen(
    navController: NavController,
    movieDetailParcelable: MovieDetailParcelable
) {
    navController.currentBackStackEntry?.arguments = Bundle().apply {
        putParcelable(DETAIL_SCREEN_ARGUMENT, movieDetailParcelable)
    }
    navController.navigate(MOVIE_DETAIL_SCREEN)
}

@Composable
fun TopAppBar(
    iconLeft: ImageVector = Icons.Default.Menu,
    iconRight: ImageVector = Icons.Default.Search,
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Blumine),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(imageVector = iconLeft, contentDescription = "Menu", tint = Color.White)
            Text(text = title, color = Color.White, fontSize = 18.sp)
            Icon(
                imageVector = iconRight,
                contentDescription = "Search",
                tint = Color.White
            )
        }

    }
}


@Composable
fun MovieManListGridItem(
    movieData: MovieResponseDtoItem,
    onItemClick: (MovieResponseDtoItem) -> Unit
) {

    Column {
        Box(
            modifier = Modifier.shadow(
                elevation = 25.dp
            ),
        ) {
            Box(
                modifier = Modifier
                    .height(155.dp)
                    .width(110.dp)
                    .clickable {
                        onItemClick(movieData)
                    }.background(Color.LightGray)
            ) {
                Image(
                    painter = rememberImagePainter(data = movieData.image?.medium),
                    contentDescription = movieData.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
    }


}


@Composable
fun HorizontalPager(
    horizontalPagerData: List<MovieResponseForHorizontalPagerDtoItem>,
    onItemClick: (MovieResponseForHorizontalPagerDtoItem) -> Unit,
    isHorizontalPagerLoading: Boolean,
    retry: () -> Unit
) {
    Box(
        modifier = Modifier.height(151.dp)
    ) {
        if (horizontalPagerData.isNotEmpty()) {
            val pagerState = remember {
                PagerState(maxPage = horizontalPagerData.size - 1)
            }
            Pager(state = pagerState) {
                val movie = horizontalPagerData[page]
                val isSelected = pagerState.currentPage == page

                val filteredOffset = if (abs(pagerState.currentPage - page) < 2) {
                    currentPageOffset
                } else 0f
                Row {
                    Spacer(modifier = Modifier.size(10.dp))
                    MoviePagerItem(
                        movie = movie,
                        isSelected = isSelected,
                        offset = filteredOffset,
                        onItemClick = onItemClick
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }


            }

        } else if (isHorizontalPagerLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Check your Internet connection", color = Color.White)
                    Button(onClick = { retry() }) {
                        Text(text = "Retry", color = Color.White)
                    }
                }
            }
        }

    }

}

@Composable
fun MoviePagerItem(
    movie: MovieResponseForHorizontalPagerDtoItem, isSelected: Boolean,
    offset: Float,
    onItemClick: (MovieResponseForHorizontalPagerDtoItem) -> Unit
) {
    val animateHeight = getOffsetBasedValue(
        selectedValue = 150,
        nonSelectedValue = 137,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateWidth = getOffsetBasedValue(
        selectedValue = 280,
        nonSelectedValue = 257,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateElevation = getOffsetBasedValue(
        selectedValue = 12,
        nonSelectedValue = 2,
        isSelected = isSelected,
        offset = offset
    ).dp

    Card(
        elevation = animateDpAsState(animateElevation).value,
        modifier = Modifier
            .width(animateWidth)
            .height(animateHeight)
            .clickable {
                onItemClick(movie)
            }.background(Color.LightGray),
        shape = RoundedCornerShape(5.dp),
    ) {
        Image(
            painter = rememberImagePainter(data = movie.show?.image?.original ?: "nan"),
            contentDescription = movie.show?.name ?: "name was not given",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

private fun getOffsetBasedValue(
    selectedValue: Int,
    nonSelectedValue: Int,
    isSelected: Boolean,
    offset: Float,
): Float {
    val actualOffset = if (isSelected) 1 - abs(offset) else abs(offset)
    val delta = abs(selectedValue - nonSelectedValue)
    val offsetBasedDelta = delta * actualOffset

    return min(selectedValue, nonSelectedValue) + offsetBasedDelta
}