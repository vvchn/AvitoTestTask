package com.vvchn.avitotesttask.presentation.moviedetailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vvchn.avitotesttask.presentation.ui.theme.mainBackground
import com.vvchn.avitotesttask.presentation.ui.theme.topBarColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    vm: MovieDetailScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState: MovieDetailState by vm.state.collectAsStateWithLifecycle()

    Surface(color = mainBackground, modifier = Modifier
        .fillMaxSize()
        .background(mainBackground)) {
        Column {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else {
                TopAppBar(title = { Text(text = uiState.movie?.name ?: "is that a joke?") }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "backToMainScreen",
                            tint = Color.White
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = topBarColor))

            }
        }
    }

}