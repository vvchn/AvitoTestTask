package com.vvchn.avitotesttask.presentation.mainscreen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.presentation.Dimens
import com.vvchn.avitotesttask.presentation.moviedetailscreen.MovieDetailScreenViewModel
import com.vvchn.avitotesttask.presentation.navgraph.Route

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    navController: NavController,
    movieInfo: MovieInfo?,
    context: Context,
    movieDetailVM: MovieDetailScreenViewModel,
) {
    Box(
        modifier = Modifier
            .heightIn(Dimens.movieCardMaxHeight)
            .widthIn(Dimens.movieCardMaxWidth)
            .clickable {

                movieDetailVM.pushMovie(movieInfo)
                navController.navigate(Route.MovieDetailScreen.route)
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
                    model = movieInfo?.moviePoster?.previewUrl,
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
                    text = "★" + String.format("%.1f", movieInfo?.ratingKP?.kp ?: "0"),
                    textAlign = TextAlign.Left,
                    color = Color.Yellow,
                )
                Spacer(modifier = Modifier.widthIn(5.dp))
                Text(
                    text = movieInfo?.year?.toString() ?: stringResource(R.string.unknown),
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