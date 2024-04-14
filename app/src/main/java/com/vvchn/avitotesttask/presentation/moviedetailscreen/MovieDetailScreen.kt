package com.vvchn.avitotesttask.presentation.moviedetailscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.domain.models.PersonInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.presentation.Dimens
import com.vvchn.avitotesttask.presentation.ui.theme.mainBackground
import com.vvchn.avitotesttask.presentation.ui.theme.searchBarColor
import com.vvchn.avitotesttask.presentation.ui.theme.topBarColor


private fun String.clearFromBrackets(): String {
    return this.replace("[", "").replace("]", "")
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailScreen(
    vm: MovieDetailScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val posters = vm.postersFlow.collectAsLazyPagingItems()
    val reviews = vm.reviewsFlow.collectAsLazyPagingItems()
    val persons = vm.personsFlow.collectAsLazyPagingItems()

    val context = LocalContext.current

    val uiState: MovieDetailScreenState by vm.state.collectAsStateWithLifecycle()
    var isTextExpanded by remember { mutableStateOf(false) }

    Surface(
        color = mainBackground, modifier = Modifier
            .fillMaxSize()
            .background(mainBackground)
    ) {

        Scaffold(containerColor = mainBackground,
            topBar = {
                TopAppBar(title = {
                    Row {
                        Text(
                            text = "★".repeat(uiState.movie?.ratingKP?.kp?.toInt() ?: 1) + "  ",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Yellow,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = String.format("%.1f", uiState.movie?.ratingKP?.kp ?: "0"),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "backToMainScreen",
                            tint = Color.White
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = topBarColor))
            }
        ) {
            if (uiState.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp)
                            .heightIn(max = 250.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        GlideImage(
                            model = uiState.movie?.moviePoster?.previewUrl,
                            contentDescription = "moviePoster",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(150.dp)
                                .width(100.dp)
                                .clip(shape = MaterialTheme.shapes.small)
                        ) { image ->
                            image.error(R.drawable.not_found)
                                .placeholder(R.drawable.placeholder_image)
                                .apply(RequestOptions().fitCenter())
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(10.dp)
                        )
                        Column(modifier = Modifier.fillMaxWidth(fraction = 0.9f)) {
                            Text(
                                text = uiState.movie?.name ?: stringResource(id = R.string.unknown),
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier,
                            )
                            Text(
                                text = uiState.movie?.alternativeName ?: "",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White,
                            )
                            Text(
                                text = uiState.movie?.type ?: "",
                                color = Color.White,
                            )
                            Text(
                                text = uiState.movie?.genres?.map { genre -> genre.name }
                                    ?.toString()
                                    ?.clearFromBrackets()
                                    ?: stringResource(id = R.string.unknown),
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White,
                                fontSize = Dimens.movieInformationSize,
                            )
                            Text(
                                text = uiState.movie?.countries?.map { country -> country.name }
                                    ?.toString()?.clearFromBrackets()
                                    ?: stringResource(id = R.string.unknown),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White,
                                fontSize = Dimens.movieInformationSize,
                            )
                            Text(
                                text = (uiState.movie?.let { movie -> movie.year.toString() }
                                    ?: stringResource(id = R.string.no_data)) + ", " + uiState.movie?.let { movie -> movie.ageRating.toString() } + "+   " + (uiState.movie?.ratingMpaa
                                    ?: ""),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = Dimens.movieInformationSize,
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .alpha(0.3f),
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                            .clickable { isTextExpanded = !isTextExpanded },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = uiState.movie?.description
                                ?: stringResource(id = R.string.unknown),
                            color = Color.White,
                            textAlign = TextAlign.Left,
                            lineHeight = 20.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = if (isTextExpanded) Int.MAX_VALUE else 3,
                            modifier = Modifier.weight(0.8f)
                        )
                        Icon(
                            imageVector = if (isTextExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "expandText",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .alpha(0.3f),
                        color = Color.White
                    )

                    if (posters.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        if (posters.itemCount > 0) {
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height = 200.dp)
                                    .padding(top = 20.dp, bottom = 20.dp)
                            ) {
                                items(
                                    count = posters.itemCount,
                                    key = null,
                                    contentType = posters.itemContentType { "Poster" },
                                ) { poster ->
                                    PosterImage(posterInfo = posters[poster])
                                }
                                item {
                                    if (posters.loadState.append is LoadState.Loading) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .wrapContentSize(Alignment.Center),
                                text = stringResource(id = R.string.noPosters),
                                color = Color.White,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                fontSize = Dimens.nothingFoundTextSize,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .alpha(0.3f),
                        color = Color.White
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(60.dp)
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ((("${context.getString(R.string.production)} " + (uiState.productionList?.docs?.map { studioInfo -> studioInfo.title }
                                ?.toString()?.replace("[", "")?.replace("]", "")
                                ?: context.getString(R.string.unknown))))),
                            textAlign = TextAlign.Left,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .alpha(0.3f),
                        color = Color.White
                    )

                    if (persons.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        if (persons.itemCount > 0) {
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height = 250.dp)
                                    .padding(top = 20.dp, bottom = 20.dp)
                            ) {
                                items(
                                    count = persons.itemCount,
                                    key = null,
                                    contentType = persons.itemContentType { "Person" },
                                ) { person ->
                                    PersonItem(personInfo = persons[person])
                                }
                                item {
                                    if (persons.loadState.append is LoadState.Loading) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .wrapContentSize(Alignment.Center),
                                text = stringResource(id = R.string.no_data),
                                color = Color.White,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                fontSize = Dimens.nothingFoundTextSize,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .alpha(0.3f),
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(50.dp)
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ("${context.getString(R.string.reviews)} " + uiState.reviewsCount) + " " + context.getString(
                                R.string.reviewInstruction
                            ),
                            textAlign = TextAlign.Left,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .alpha(0.3f),
                        color = Color.White
                    )

                    if (reviews.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        if (reviews.itemCount > 0) {
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 20.dp, bottom = 20.dp)
                            ) {
                                items(
                                    count = reviews.itemCount,
                                    key = null,
                                    contentType = reviews.itemContentType { "Review" },
                                ) { review ->
                                    ReviewItem(reviewInfo = reviews[review], context = context)
                                }
                                item {
                                    if (reviews.loadState.append is LoadState.Loading) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .wrapContentSize(Alignment.Center),
                                text = stringResource(id = R.string.noReviews),
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

    }

    LaunchedEffect(key1 = posters.loadState) {
        if (posters.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (posters.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = reviews.loadState) {
        if (reviews.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (reviews.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PosterImage(posterInfo: PosterInfo?) {
    GlideImage(
        model = posterInfo?.previewUrl,
        contentDescription = "moviePreview",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .heightIn(max = 140.dp)
            .widthIn(max = 200.dp)
            .clip(shape = MaterialTheme.shapes.small)
            .padding(horizontal = 10.dp)
    ) {
        it.error(R.drawable.not_found).placeholder(R.drawable.placeholder_image)
            .apply(RequestOptions().fitCenter())
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PersonItem(personInfo: PersonInfo?) {
    GlideImage(
        model = personInfo?.photo,
        contentDescription = "personPhoto",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(180.dp)
            .width(140.dp)
            .clip(shape = MaterialTheme.shapes.small)
            .padding(bottom = 5.dp)
    ) {
        it.error(R.drawable.not_found).placeholder(R.drawable.placeholder_image)
            .apply(RequestOptions().fitCenter())
    }
    Text(
        text = personInfo?.name ?: "",
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Text(
        text = personInfo?.profession.toString().replace("[", "").replace("]", ""),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
private fun ReviewItem(reviewInfo: ReviewInfo?, context: Context) {
    var isTextExpanded by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .background(searchBarColor)
            .width(350.dp)
            .padding(20.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { isTextExpanded = !isTextExpanded }
    )
    {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 8.dp)
        ) {
            Text(
                text = ("${context.getString(R.string.author)} " + (reviewInfo?.author
                    ?: context.getString(R.string.unknown))),
                color = Color.White, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold
            )
            Text(
                text = ("${context.getString(R.string.date)} " + (reviewInfo?.date?.toString()
                    ?: context.getString(R.string.unknown))),
                color = Color.White, fontWeight = FontWeight.Bold
            )
            Text(
                text = ("${context.getString(R.string.reviewTitle)} " + (reviewInfo?.title
                    ?: context.getString(R.string.unknown))),
                color = Color.White, fontWeight = FontWeight.Bold
            )
            Text(
                text = ("${context.getString(R.string.reviewType)} " + (reviewInfo?.type
                    ?: context.getString(R.string.unknown))),
                color = Color.White, fontWeight = FontWeight.Bold
            )
            Text(
                text = (reviewInfo?.review ?: ""),
                color = Color.White,
                maxLines = if (isTextExpanded) Int.MAX_VALUE else 5,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}