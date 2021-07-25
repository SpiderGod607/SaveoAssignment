package com.spidergod.saveoassignment.ui.presentation.movie_home_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.spidergod.saveoassignment.data.remote.dto.movie_response_dto.MovieResponseDtoItem
import com.spidergod.saveoassignment.data.remote.dto.movie_response_for_horizontal_pager_dto.MovieResponseForHorizontalPagerDtoItem
import com.spidergod.saveoassignment.ui.componenets.movie_pager.Pager
import com.spidergod.saveoassignment.ui.componenets.movie_pager.PagerState
import com.spidergod.saveoassignment.ui.theme.Blumine
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
                item {
                    HorizontalPager(viewModel.horizontalPagerData)
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
                                        movieData = it1
                                    )
                                }
                                if (currentItemIndex < movieListItems.itemCount - 1) {
                                    movieListItems[currentItemIndex + 1]?.let { it1 ->
                                        MovieManListGridItem(
                                            movieData = it1
                                        )
                                    }
                                }
                                if (currentItemIndex < movieListItems.itemCount - 2) {
                                    movieListItems[currentItemIndex + 2]?.let { it1 ->
                                        MovieManListGridItem(
                                            movieData = it1
                                        )
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




@Composable
fun TopAppBar(title: String) {
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
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            Text(text = title, color = Color.White, fontSize = 18.sp)
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }

    }
}


@Composable
fun MovieManListGridItem(
    movieData: MovieResponseDtoItem
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
fun HorizontalPager(horizontalPagerData: List<MovieResponseForHorizontalPagerDtoItem>) {
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
                        offset = filteredOffset
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }


            }

        }

    }

}

@Composable
fun MoviePagerItem(
    movie: MovieResponseForHorizontalPagerDtoItem, isSelected: Boolean,
    offset: Float
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
            .height(animateHeight),
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