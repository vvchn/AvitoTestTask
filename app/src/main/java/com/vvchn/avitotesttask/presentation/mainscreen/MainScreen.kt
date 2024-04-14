package com.vvchn.avitotesttask.presentation.mainscreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.presentation.Dimens
import com.vvchn.avitotesttask.presentation.moviedetailscreen.MovieDetailScreenViewModel
import com.vvchn.avitotesttask.presentation.navgraph.Route
import com.vvchn.avitotesttask.presentation.ui.theme.mainBackground
import com.vvchn.avitotesttask.presentation.ui.theme.searchBarColor
import com.vvchn.avitotesttask.presentation.ui.theme.topBarColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainScreenVM: MainScreenViewModel,
    movieDetailScreenViewModel: MovieDetailScreenViewModel
) {

    val uiState: MainScreenState by mainScreenVM.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val movies = mainScreenVM.mainScreenFlow.collectAsLazyPagingItems()

    var isSearchedOnce = false
    var isUserTriesToSearch by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.catalogue), color = Color.White)
            },
            actions = {
                IconButton(onClick = {
                    isUserTriesToSearch = !isUserTriesToSearch
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
                IconButton(onClick = {
                    navController.navigate(Route.Filters.route)
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Filters", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = topBarColor)
        )

        if (isUserTriesToSearch) {
            Surface(
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.searchFieldBarHeight)
                    .background(searchBarColor),
            )
            {
                TextField(
                    value = uiState.userSearchInput,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_movie_by_name),
                            color = Color.White
                        )
                    },
                    onValueChange = {
                        mainScreenVM.collectUserInput(it)
                        uiState.debounceJob?.cancel()
                        uiState.debounceJob = uiState.debounceScope.launch {
                            delay(1000L)
                            if (uiState.userSearchInput == it) {
                                mainScreenVM.searchMovies()
                                movies.refresh()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedContainerColor = searchBarColor,
                        focusedContainerColor = searchBarColor,
                        unfocusedIndicatorColor = searchBarColor,
                        focusedIndicatorColor = searchBarColor,
                    ),
                    maxLines = 1,
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        isUserTriesToSearch = !isUserTriesToSearch
                        mainScreenVM.searchMovies()
                    }),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.contentHorizontalPadding)

        ) {
            if (movies.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                if (movies.itemCount > 0) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(Dimens.gridCellsMinSize),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.cellsArrangement),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.cellsArrangement),
                    ) {
                        items(
                            count = movies.itemCount,
                            key = null,
                            contentType = movies.itemContentType { "Movie" },
                        )
                        { movie ->
                            MovieItem(
                                movieInfo = movies[movie],
                                context = context,
                                navController = navController,
                                movieDetailVM = movieDetailScreenViewModel,
                            )
                        }
                        item {
                            if (movies.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                } else {
                    Surface(
                        shape = RectangleShape,
                        color = mainBackground,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center),
                            text = stringResource(id = R.string.nothing_found),
                            color = Color.White,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            fontSize = Dimens.nothingFoundTextSize,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

