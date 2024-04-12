package com.vvchn.avitotesttask.presentation.mainscreen

import android.content.Context
import android.util.Range
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.presentation.Dimens
import com.vvchn.avitotesttask.presentation.ui.theme.mainBackground
import com.vvchn.avitotesttask.presentation.ui.theme.searchBarColor
import com.vvchn.avitotesttask.presentation.ui.theme.topBarColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainScreenVM: MainScreenViewModel = viewModel()
) {
    var isSearchedOnce = false

    val context = LocalContext.current
    val movies = mainScreenVM.mainScreenFlow.collectAsLazyPagingItems()

    val gridState = rememberLazyGridState()

    var isUserTriesToSearch by remember { mutableStateOf(false) }
    var isUserTriesToSetFilters by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf(mainScreenVM.state.value.userSearchInput) }

    val focusManager = LocalFocusManager.current

    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.catalogue), color = Color.White)
            },
            actions = {
                IconButton(onClick = {
                    if (isUserTriesToSetFilters) {
                        isUserTriesToSetFilters = !isUserTriesToSetFilters
                    }
                    isUserTriesToSearch = !isUserTriesToSearch
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
                IconButton(onClick = {
                    if (isUserTriesToSearch) {
                        isUserTriesToSearch = !isUserTriesToSearch
                    }
                    isUserTriesToSetFilters = !isUserTriesToSetFilters
                }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Filters", tint = Color.White)
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
                    value = userInput,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_movie_by_name),
                            color = Color.White
                        )
                    },
                    onValueChange = { userInput = it },
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
                        mainScreenVM.searchMovies(userInput)
                    }),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                )
            }
        }

        if (isUserTriesToSetFilters) {
            FiltersBar(mainScreenVM)
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
                        state = gridState,
                    ) {
                        items(
                            count = movies.itemCount,
                            key = null,
                            contentType = movies.itemContentType { "Movie" },
                        )
                        { movie ->
                            MovieItem(movieInfo = movies[movie], context = context)
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    movieInfo: MovieInfo?,
    context: Context,
) {
    Box(
        modifier = Modifier
            .heightIn(Dimens.movieCardMaxHeight)
            .widthIn(Dimens.movieCardMaxWidth)
            .clickable {
                Toast
                    .makeText(
                        context,
                        "${movieInfo!!.id}",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
    ) {
        Column(
            modifier = Modifier.padding(Dimens.movieCardContentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .heightIn(max = Dimens.movieCardMaxHeight - 50.dp)
                    .widthIn(max = Dimens.movieCardMaxWidth)
            ) {
                GlideImage(
                    model = movieInfo!!.moviePoster?.previewUrl,
                    contentDescription = "moviePreview",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = MaterialTheme.shapes.small)
                ) {
                    it.error(R.drawable.not_found).placeholder(R.drawable.placeholder_image)
                        .apply(RequestOptions().fitCenter())
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.movieShortInfoSpacing)) {
                Text(
                    text = "★" + String.format("%.1f", movieInfo!!.ratingKP!!.kp),
                    textAlign = TextAlign.Left,
                    color = Color.Yellow,
                )
                Spacer(modifier = Modifier.widthIn(5.dp))
                Text(
                    text = movieInfo?.year.toString() ?: stringResource(R.string.unknown),
                    textAlign = TextAlign.Left,
                    color = Color.White,
                )
            }
            Text(
                text = movieInfo?.name ?: "${R.string.unknown}",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun YearFilter(vm: MainScreenViewModel) {
    var yearLeftBound by remember { mutableStateOf(vm.state.value.yearLeftBound) }
    var yearRightBound by remember { mutableStateOf(vm.state.value.yearRightBound) }

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = stringResource(id = R.string.years),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .width(75.dp),
            value = yearLeftBound,
            placeholder = {
                Text(text = "1800")
            },
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            onValueChange = {
                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                    yearLeftBound = it
                    vm.state.value.yearLeftBound = yearLeftBound
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(id = R.string.from)) },
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier = Modifier.width(10.dp))
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .width(75.dp),
            value = yearRightBound,
            placeholder = {
                Text(text = "2024")
            },
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            onValueChange = {
                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                    yearRightBound = it
                    vm.state.value.yearRightBound = yearRightBound
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(id = R.string.to)) },
            maxLines = 1,
            singleLine = true
        )
    }
}

@Composable
private fun AgeFilter(vm: MainScreenViewModel) {
    var ageRatingLeftBound by remember { mutableStateOf(vm.state.value.ageRatingLeftBound) }
    var ageRatingRightBound by remember { mutableStateOf(vm.state.value.ageRatingRightBound) }

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = stringResource(id = R.string.age),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .width(75.dp),
            value = ageRatingLeftBound,
            placeholder = {
                Text(text = "0")
            },
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            onValueChange = {
                if (it.length <= 2 && it.all { char -> char.isDigit() }) {
                    ageRatingLeftBound = it
                    vm.state.value.yearLeftBound = ageRatingLeftBound
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(id = R.string.from)) },
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier = Modifier.width(10.dp))
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .width(75.dp),
            value = ageRatingRightBound,
            placeholder = {
                Text(text = "18")
            },
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            onValueChange = {
                if (it.length <= 2 && it.all { char -> char.isDigit() }) {
                    ageRatingRightBound = it
                    vm.state.value.yearRightBound = ageRatingRightBound
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(id = R.string.to)) },
            maxLines = 1,
            singleLine = true
        )
    }
}

@Composable
private fun FiltersBar(vm: MainScreenViewModel) {
    Surface(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.filtersBarHeight)
            .background(searchBarColor),
        color = searchBarColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.filters),
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            YearFilter(vm = vm)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            AgeFilter(vm = vm)
        }
    }
}