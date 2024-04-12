package com.vvchn.avitotesttask.presentation.mainscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.presentation.ui.theme.searchBarColor
import com.vvchn.avitotesttask.presentation.ui.theme.topBarColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainScreenVM: MainScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    val movies = mainScreenVM.mainScreenFlow.collectAsLazyPagingItems()

    val gridState = rememberLazyGridState()

    var isUserTriesToSearch by remember { mutableStateOf(false) }
    var isUserTriesToSetFilters by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf(mainScreenVM.state.value.userInput) }

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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
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
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(searchBarColor),
                color = searchBarColor,
            ) {
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)

        ) {
            if (movies.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
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
    val maxHeight = 240.dp
    val maxWith = 120.dp

    Box(
        modifier = Modifier
            .heightIn(maxHeight)
            .widthIn(maxWith)
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
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .heightIn(max = maxHeight - 50.dp)
                    .widthIn(max = maxWith)
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
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
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