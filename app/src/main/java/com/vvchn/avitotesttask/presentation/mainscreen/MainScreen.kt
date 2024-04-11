package com.vvchn.avitotesttask.presentation.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vvchn.avitotesttask.domain.models.MovieInfo


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainScreenVM: MainScreenViewModel = viewModel()
) {
    val lazyPagingItems = mainScreenVM.mainScreenFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(fraction = 0.95f),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            /* падает с ошибкой:
            java.lang.IllegalArgumentException: Key "ВСТАВИТЬ ЧИСЛО" was already used.
            If you are using LazyColumn/Row please make sure you provide a unique key for each item. */
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { movie -> movie.id!! + movie.id.hashCode() },
            contentType = lazyPagingItems.itemContentType { "Movie" },
        ) { index ->
            val movie = lazyPagingItems[index]

            movie?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFFE7E3D2))
                        .heightIn(min = 180.dp)
                        .widthIn(min = 100.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                    ) {
                        Text(text = movie.name!!, color = Color.Black)
                        Text(text = movie.year.toString(), color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieInfoItem(
    movieInfo: MovieInfo,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .width(240.dp)
            .heightIn(100.dp),
    ) {
        Text(
            text = movieInfo.name!!,
            modifier = Modifier
                .size(15.dp)
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = "${movieInfo.year ?: "неизвестно"}",
            modifier = Modifier
                .size(15.dp)
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}